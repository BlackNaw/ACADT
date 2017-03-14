/**
 * Created by Juangra on 02/03/2017.
 */
FRUTA.namespace("FRUTA.Cliente");
(function (g) {
    var ajax = FRUTA.Ajax;
    var llamada;
    var nombreCliente = document.getElementById("cuadroBienvenida");
    var cliente = JSON.parse(sessionStorage.getItem("cliente"));
    nombreCliente.innerHTML = "Bienvenido " + "<br>" + cliente.nombre;
    var opcion = document.getElementById('seleccionFruta');
    document.getElementById('carritoAnadir').style.display = "none";


    FRUTA.Cliente.crearTabla = function () {
        var mensaje = llamada.req.responseText;
        if (mensaje.startsWith("Error")) {
            swal(mensaje, "", "error");
        } else {
            var almacenStock = JSON.parse(mensaje);
            var tabla = document.getElementById("tablaFruta");
            FRUTA.Cliente.borrarTabla(tabla);
            var contador = 0;
            almacenStock.forEach(function (valor) {
                var nodo = document.createElement("div");
                nodo.setAttribute("class", "table-row item");
                nodo.innerHTML = "<img src=''>" +
                    "<div class ='info' id = 'info" + contador + "'>" +
                    "<input type='hidden' value='" + valor.idDistribucion + "' id='idDistribucion" + contador + "'>" +
                    "<div id='nombreAlmacen" + contador + "'>" + valor.nombreAlmacen + "</div>" +
                    "<div id='nombreVariedad" + contador + "'>" + valor.nombreVariedad + "</div>" +
                    "<div id='nombreMedida" + contador + "'>" + valor.nombreMedida + "</div>" +
                    "<div id='precio" + contador + "'>" + FRUTA.Cliente.extraerDecimales(valor.precio, 2) + " &#8364</div>" +
                    "<div id='stock" + contador + "'>" + valor.stock + "</div>" +
                    "<div><a href='#' id ='menos" + contador + "' ><img src=\"img/menos.png\"></a>" +
                    "<input type=\"number\" value='0' min ='0' max='" + valor.stock + "' id='cantidad" + contador + "'>" +
                    "<a id='mas" + contador + "' href='#'><img src=\"img/mas.png\"></a>" +
                    "<a href='#'  id='borrar" + contador + "'><img src=\"img/papelera.png\"></a></div>" +
                    "</div>";

                document.getElementById("tablaFruta").appendChild(nodo);
                document.getElementById("mas" + contador).addEventListener("click", function () {
                    FRUTA.Cliente.aumentar(this.id.substring(3, contador.length), valor.stock);
                });
                document.getElementById("menos" + contador).addEventListener("click", function () {
                    FRUTA.Cliente.disminuir(this.id.substring(5, contador.length), valor.stock);
                });
                document.getElementById("borrar" + contador).addEventListener("click", function () {
                    FRUTA.Cliente.borrar(this.id.substring(6, contador.length));
                });
                document.getElementById("cantidad" + contador).addEventListener("change", function () {
                    FRUTA.Cliente.comprobarCantidad(valor.stock, this.id.substring(8, contador.length));
                });
                document.getElementById("cantidad" + contador).addEventListener("change", function () {
                    FRUTA.Cliente.comprobarCantidad(valor.stock, this.id.substring(8, contador.length));
                });
                document.getElementById("cantidad" + contador).addEventListener("change", function () {
                    FRUTA.Cliente.comprobarCantidad(valor.stock, this.id.substring(8, contador.length));
                });
                contador++;
            });
        }
    };

    FRUTA.Cliente.rellenarCuerpoTabla = function () {
        var contador = 0;
        var total = 0;
        var datosCarrito = JSON.parse(sessionStorage.getItem("carrito"));
        var tablaFruta = document.getElementById("tablaFruta");
        FRUTA.Cliente.borrarTabla(tablaFruta);
        datosCarrito.forEach(function (valor) {
            var nodoCuerpo = document.createElement("div");
            nodoCuerpo.setAttribute("class", "table-row item");
            nodoCuerpo.setAttribute("id", "fila" + contador);
            nodoCuerpo.innerHTML = "<img src=''>" +
                "<div class ='info' id = 'info" + contador + "'>" +
                "<input type='hidden' value='" + valor.idDistribucion + "' id='idDistribucion" + contador + "'>" +
                "<div id='nombreAlmacen" + contador + "'>" + valor.almacen + "</div>" +
                "<div id='nombreFruta" + contador + "'>" + valor.fruta + "</div>" +
                "<div id='nombreVariedad" + contador + "'>" + valor.variedad + "</div>" +
                "<div id='nombreMedida" + contador + "'>" + valor.medida + "</div>" +
                "<div id='precio" + contador + "'>" + valor.precio + "</div>" +
                "<input type='number' value=" + valor.cantidad + " min='0' max='" + valor.stock + "' onchange='FRUTA.Cliente.comprobarCantidad(" + valor.stock + "," + contador + ",true)' id='cantidad" + contador + "'>" +
                "<div id='subtotal" + contador + "'>" + FRUTA.Cliente.extraerDecimales(valor.cantidad * parseFloat(valor.precio.substring(0, valor.precio.length - 2)), 2) + "  &#8364" +
                "<span class='borrarSpan'><a href='#' id='lineaEliminar" + contador + "'><img src=\"img/borrarElemento.png\" class='borrarImg'></a></div></span>" +
                "</div>";
            total = Number(parseFloat(total) + (parseFloat(FRUTA.Cliente.extraerDecimales(valor.cantidad * parseFloat(valor.precio.substring(0, valor.precio.length - 2)), 2)))).toFixed(2);
            document.getElementById("tablaFruta").appendChild(nodoCuerpo);
            document.getElementById("lineaEliminar" + contador).addEventListener("click", function () {
                FRUTA.Carrito.borrarLinea(valor.idDistribucion);
            });
            contador++;
        });
        var nodoTotal = document.createElement("div");
        nodoTotal.setAttribute("class", "table-total item");
        nodoTotal.setAttribute("id", "filaTotal");
        nodoTotal.innerHTML = "<img src=''>" +
            "<div class='info'>" +
            "<div><strong id='productos'> Total productos: " + contador + "</strong></div>" +
            "<div><strong id='totalCompra'> Total compra: " + total + "</strong></div>" +
            "</div>";
        document.getElementById("tablaFruta").appendChild(nodoTotal);
        var nodoBoton = document.createElement("div");
        nodoBoton.setAttribute("class", "table-row item");
        nodoBoton.setAttribute("id", "filaBoton");
        nodoBoton.innerHTML = "<div class = 'info infoButton'>" +
            "<input type='button' id='btnComprar' class='action-button shadow animate blue' value='Comprar'>" +
            "</div>";
        document.getElementById("tablaFruta").appendChild(nodoBoton);
        var btnComprar = document.getElementById("btnComprar");
        btnComprar.addEventListener("click", function () {
            //Este valor es para decir que se ha comprado y realizamos el pedido
            //Es decir hemos finalizado el pedido
            FRUTA.Carrito.comprar(1);
        });
    };

    FRUTA.Cliente.dibujarCabeceraCarrito = function (cabeceraTabla) {
        cabeceraTabla.innerHTML = "<div id='cabAlmacen'>Almac&eacuten</div>" +
            "<div id='cabFruta'>Fruta</div>" +
            "<div id='cabVariedad'>Variedad</div>" +
            "<div id='cabMedida'>Medida</div>" +
            "<div id='cabPrecio'>Precio</div>" +
            "<div id='cabCantidad'>Cantidad</div>" +
            "<div id='cabSubtotal'>Subtotal</div>";
    };

    FRUTA.Cliente.obtenerTotal = function (contador, antes) {
        var restar = document.getElementById("subtotal" + contador).innerHTML;
        var total = document.getElementById("totalCompra").innerHTML;
        total=total.substring(14, total.length);

        if (Number(parseFloat(antes)) > Number(parseFloat(restar)))
            return (parseFloat(total) - parseFloat(restar)).toFixed(2);
        else
            return (parseFloat(total) + parseFloat(restar)).toFixed(2);
    };

    function comprobarCantidadCarrito(contador, stock, carrito) {
        var str = document.getElementById("precio" + contador).innerHTML;
        var sub = document.getElementById("subtotal" + contador).innerHTML;

        var precio = parseFloat(str.substring(0, str.length - 2));
        var cantidad = parseFloat(document.getElementById("cantidad" + contador).value);
        var total = (cantidad * precio).toFixed(2);

        document.getElementById("subtotal" + contador).innerHTML = total + " &#8364" + "<span class='borrarSpan'><a href='#' id='lineaEliminar" + contador + "'><img src=\"img/borrarElemento.png\" class='borrarImg'></a></div></span>";

        document.getElementById("totalCompra").innerHTML = "Total compra: " + FRUTA.Cliente.obtenerTotal(contador, sub.substring(0, sub.length - 122));

        document.getElementById("lineaEliminar" + contador).addEventListener("click", function () {
            FRUTA.Carrito.borrarLinea(document.getElementById("idDistribucion" + contador).value);
        });
        if (document.getElementById("cantidad" + contador).value < 1 || document.getElementById("cantidad" + contador).value > stock) {
            document.getElementById("cantidad" + contador).value = 0;
            FRUTA.Carrito.eliminarCarrito(document.getElementById("idDistribucion" + contador).value);
            if (carrito) {
                FRUTA.Carrito.borrarLinea("idDistribucion" + contador);
            }

        } else {
            FRUTA.Carrito.anadirCarrito(contador, stock);
        }
        return cantidad;
    }

    FRUTA.Cliente.comprobarCantidad = function (stock, contador, carrito) {

        if (carrito) {
            var cantidad = comprobarCantidadCarrito(contador, stock, carrito);
        }
        if (document.getElementById("cantidad" + contador).value < 1 || document.getElementById("cantidad" + contador).value > stock) {
            document.getElementById("cantidad" + contador).value = 0;
            FRUTA.Carrito.eliminarCarrito(document.getElementById("idDistribucion" + contador).value);
            if (carrito) {
                FRUTA.Carrito.borrarLinea("idDistribucion" + contador);
            }
        } else {
            FRUTA.Carrito.anadirCarrito(contador, stock);
        }
    };

    FRUTA.Cliente.aumentar = function (contador, stock) {
        document.getElementById("cantidad" + contador).value = parseInt(document.getElementById("cantidad" + contador).value) + 1;
        FRUTA.Cliente.comprobarCantidad(stock, contador);
    };

    FRUTA.Cliente.disminuir = function (contador, stock) {
        document.getElementById("cantidad" + contador).value = parseInt(document.getElementById("cantidad" + contador).value) - 1;
        FRUTA.Cliente.comprobarCantidad(stock, contador);
    };

    FRUTA.Cliente.borrar = function (contador) {
        document.getElementById("cantidad" + contador).value = 0;
        FRUTA.Carrito.eliminarCarrito(document.getElementById("idDistribucion" + contador));
    };

    opcion.addEventListener("change", function () {
        var envio = {
            codFruta: opcion.value
        };
        var json = JSON.stringify(envio);
        llamada = new ajax.CargadorContenidos("/ServletAlmacenStock", FRUTA.Cliente.crearTabla, json);

    }, true);
    document.getElementById("salir").addEventListener("click", function () {
        sessionStorage.clear();
        window.location.href = "salirSesion.jsp";
    });

    FRUTA.Cliente.dibujarCabecera = function (cabeceraTabla) {
        cabeceraTabla.innerHTML = "<div id='cabAlmacen'>Almac&eacuten</div>" +
            "<div id='cabVariedad'>Variedad</div>" +
            "<div id='cabMedida'>Medida</div>" +
            "<div id='cabPrecio'>Precio</div>" +
            "<div id='cabStock'>Stock</div>" +
            "<div id='cabCantidad'>Cantidad</div>";
    };

    FRUTA.Cliente.extraerDecimales = function (numero, decimales) {
        if (!decimales) decimales = 2;
        var d = Math.pow(10, decimales);
        return ((numero * d) / d).toFixed(decimales);
    };
    FRUTA.Cliente.borrarTabla = function (elemento) {
        while (elemento.hasChildNodes()) {
            elemento.removeChild(elemento.lastChild);
        }
    };
})(window);
