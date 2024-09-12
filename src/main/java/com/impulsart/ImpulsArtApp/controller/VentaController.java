package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(path = "/api/venta", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class VentaController {


    // Clase interna estática para generar códigos de referencia
    private static class CodigoReferenciaGenerator {
        private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        private static final int LONGITUD_CODIGO = 5;
        private static final SecureRandom random = new SecureRandom();

        public static String generarCodigoReferencia() {
            StringBuilder codigo = new StringBuilder(LONGITUD_CODIGO);
            for (int i = 0; i < LONGITUD_CODIGO; i++) {
                int indice = random.nextInt(CARACTERES.length());
                codigo.append(CARACTERES.charAt(indice));
            }
            return codigo.toString();
        }
    }


    //INYECCION DE DEPENDECIAS
    @Autowired
    private VentaImp ventaImp;
    @Autowired
    private ObraImp obraImp;
    @Autowired
    private DireccionImp direccionImp;
    @Autowired
    private CarritoImp carritoImp;
    @Autowired
    private UsuarioImp usuarioImp;
    @Autowired
    private ElementoCarritoImp elementoCarritoImp;
    @Autowired
    private DespachoImp despachoImp;
    @Autowired
    private EmailImp emailImp;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(
            @RequestParam("carritoId") Long carritoId,
            @RequestParam("fkCodDireccion") Long fkCodDireccion,
            @RequestParam("FkCod_Usuarios") List<Integer> usuarioIds) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Crear y configurar la venta
            Venta venta = new Venta();
            venta.setFechaVenta(LocalDate.now());

            Carrito carrito = carritoImp.findById(carritoId);
            if (carrito == null) {
                throw new IllegalArgumentException("El carrito no existe");
            }
            venta.setCarrito(carrito);

            List<ElementoCarrito> elementos = elementoCarritoImp.findByCarrito(carrito);
            BigDecimal costoTotal = BigDecimal.ZERO;
            StringBuilder detalleCompra = new StringBuilder(); // Para almacenar el detalle de la compra

            // Mapa para almacenar la información de ventas por creador
            Map<Usuario, Map<String, Integer>> ventasPorCreador = new HashMap<>();

            // Formateador de número
            NumberFormat formatoMoneda = NumberFormat.getInstance(new Locale("es", "CO"));
            formatoMoneda.setMinimumFractionDigits(0); // Sin decimales
            formatoMoneda.setMaximumFractionDigits(0);

            // Costo de envío fijo
            BigDecimal costoEnvio = BigDecimal.valueOf(10000);

            // Recorrer los elementos del carrito y calcular el costo total
            for (ElementoCarrito elemento : elementos) {
                Obra obra = obraImp.findById(elemento.getObra().getPkCod_Producto());
                if (obra != null) {
                    BigDecimal costoObra = obra.getCosto(); // Ahora es BigDecimal
                    BigDecimal subtotal = costoObra.multiply(BigDecimal.valueOf(elemento.getCantidad()));
                    costoTotal = costoTotal.add(subtotal);

                    // Obtener la cantidad actual de la obra
                    int cantidadActual = obra.getCantidad(); // Suponiendo que obra.getCantidad() devuelve un int
                    int cantidadElemento = elemento.getCantidad(); // Suponiendo que elemento.getCantidad() devuelve un int

// Calcular la nueva cantidad
                    int nuevaCantidad = cantidadActual - cantidadElemento;

// Verificar si hay suficiente cantidad disponible
                    if (nuevaCantidad < 0) {
                        throw new IllegalArgumentException("No hay suficiente cantidad disponible para la obra: " + obra.getNombreProducto());
                    }

// Actualizar la cantidad de la obra
                    obra.setCantidad(nuevaCantidad);

// Guardar los cambios
                    obraImp.update(obra);

                    // Agregar detalles del producto comprado en formato HTML
                    detalleCompra.append("<tr>")
                            .append("<td>").append(obra.getNombreProducto()).append("</td>")
                            .append("<td>").append(elemento.getCantidad()).append("</td>")
                            .append("<td>").append("$").append(formatoMoneda.format(subtotal)).append("</td>")
                            .append("</tr>");

                    // Actualizar el mapa de ventas por creador
                    List<Usuario> usuarios = obra.getUsuarios();
                    if (usuarios != null) {
                        for (Usuario usuario : usuarios) {
                            ventasPorCreador.putIfAbsent(usuario, new HashMap<>());
                            Map<String, Integer> infoVenta = ventasPorCreador.get(usuario);
                            infoVenta.merge(obra.getNombreProducto(), elemento.getCantidad(), Integer::sum);
                        }
                    }
                }
            }

            // Agregar el costo de envío al costoTotal
            BigDecimal costoTotalConEnvio = costoTotal.add(costoEnvio);

            // Asignar el costoTotal calculado a la venta
            venta.setCostoTotal(costoTotalConEnvio);

            List<Usuario> usuarios = new ArrayList<>();
            for (Integer usuarioId : usuarioIds) {
                Usuario usuario = usuarioImp.findById(usuarioId);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
            venta.setUsuarios(usuarios);

            ventaImp.create(venta);

            // Crear y configurar el despacho
            Despacho despacho = new Despacho();
            despacho.setFechaEntrega(null);
            despacho.setEstado("despachandose");

            Direccion direccion = direccionImp.findById(fkCodDireccion);
            if (direccion == null) {
                throw new IllegalArgumentException("La dirección no existe");
            }
            despacho.setDireccion(direccion);
            despacho.setVenta(venta);

            // Generar un número de referencia corto
            String referenciaUnica = CodigoReferenciaGenerator.generarCodigoReferencia();
            despacho.setReferencia(referenciaUnica);

            // Buscar un domiciliario disponible
            List<Usuario> domiciliarios = usuarioImp.findByRolNombre("DOMICILIARIO");
            if (domiciliarios.isEmpty()) {
                throw new RuntimeException("No hay domiciliarios disponibles");
            }

            // Seleccionar un domiciliario aleatoriamente
            Collections.shuffle(domiciliarios);
            Usuario domiciliario = domiciliarios.get(0);

            // Asignar el domiciliario al despacho
            despacho.setUsuario(Collections.singletonList(domiciliario));

            // Sincronizar la relación bidireccional
            domiciliario.getDespacho().add(despacho);

            despachoImp.create(despacho);

            // Notificar al domiciliario asignado
            String mensajeDomiciliario = "Se le ha asignado un nuevo despacho con el número de referencia: " + referenciaUnica +
                    "\nEl cliente debe pagar un total de: $" + formatoMoneda.format(costoTotalConEnvio) +
                    "\nCosto de envío: $" + formatoMoneda.format(costoEnvio);

            emailImp.enviarCorreoDespachoAsignado(
                    domiciliario.getEmail(),
                    "Nuevo Despacho Asignado",
                    domiciliario.getNombre(),
                    mensajeDomiciliario
            );

            // Notificar al usuario de la venta realizada, los productos y el costo total
            if (!usuarios.isEmpty()) {
                Usuario comprador = usuarios.get(0); // El primer usuario en la lista es el comprador

                // Enviar el correo al comprador
                emailImp.enviarCorreoVenta(
                        comprador.getEmail(),
                        "Confirmación de Compra",
                        comprador.getNombre(),
                        detalleCompra.toString(), // Detalles del carrito
                        "$" + formatoMoneda.format(costoTotalConEnvio) // Costo total
                );
            }

            // Notificar a los creadores de las obras
            for (Map.Entry<Usuario, Map<String, Integer>> entry : ventasPorCreador.entrySet()) {
                Usuario creador = entry.getKey();
                Map<String, Integer> obrasVendidas = entry.getValue();
                StringBuilder mensajeCreador = new StringBuilder();
                for (Map.Entry<String, Integer> obraEntry : obrasVendidas.entrySet()) {
                    String nombreObra = obraEntry.getKey();
                    Integer cantidad = obraEntry.getValue();
                    BigDecimal subtotalObra = BigDecimal.ZERO;
                    for (ElementoCarrito elemento : elementos) {
                        Obra obra = elemento.getObra();
                        if (obra.getNombreProducto().equals(nombreObra)) {
                            subtotalObra = subtotalObra.add(obra.getCosto().multiply(BigDecimal.valueOf(elemento.getCantidad())));
                        }
                    }
                    mensajeCreador.append("Le han comprado su obra '")
                            .append(nombreObra)
                            .append("' (x")
                            .append(cantidad)
                            .append(") por un total de $")
                            .append(formatoMoneda.format(subtotalObra))
                            .append("\n");
                }

                // Enviar el correo al creador
                emailImp.enviarCorreoCompraCreador(
                        creador.getEmail(),
                        "Compra de Su Obra",
                        creador.getNombre(),
                        mensajeCreador.toString()
                );
            }

            // Crear un nuevo carrito para el usuario que realizó la compra
            Usuario comprador = usuarios.get(0); // Suponemos que el primer usuario en la lista es el comprador
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(comprador);
            carritoImp.create(nuevoCarrito); // Guardar el nuevo carrito

            response.put("status", "success");
            response.put("data", "Registro Exitoso");
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("data", "Error inesperado");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//CONTROLLER DELETE
    @DeleteMapping("/delete/{PkCod_Venta}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer PkCod_Venta) {
        Map<String, Object> response = new HashMap<>();

        try {
            Venta venta = this.ventaImp.findById(PkCod_Venta);
            ventaImp.delete(venta);

            response.put("status","success");
            response.put("data","Registro Eliminado Corectamente");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

