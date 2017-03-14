/**
 * Created by Juangra on 10/03/2017.
 */
var carrito = [];
FRUTA.namespace("FRUTA.Carrito");
(function (d) {
    var ajax = FRUTA.Ajax;
    var llamada;
    sessionStorage.setItem("carrito", JSON.stringify(carrito));

    FRUTA.Carrito.capturadorEventos = function () {
        var select = document.getElementById('seleccionFruta');
        var btnCarrito = document.getElementById('carrito');
        btnCarrito.addEventListener("click", function () {
            select.style.display = "none";
            var cabecera = document.getElementById('cabeceraTabla');
            FRUTA.Cliente.borrarTabla(cabecera);
            FRUTA.Cliente.dibujarCabeceraCarrito(cabecera);
            FRUTA.Cliente.rellenarCuerpoTabla();
        });        document.getElementById("frutas").addEventListener("click", function () {
            var select = document.getElementById('seleccionFruta');
            select.style.display = "block";
            var cabecera = document.getElementById('cabeceraTabla');
            FRUTA.Cliente.dibujarCabecera(cabecera);
            var tablaFruta = document.getElementById("tablaFruta");
            FRUTA.Cliente.borrarTabla(tablaFruta);
        });
    };
    FRUTA.Carrito.anadirCarrito = function (contador,stock) {
        carrito = JSON.parse(sessionStorage.getItem("carrito"));
        var salir = false;
        var anadir = {
            "idDistribucion": document.getElementById("idDistribucion" + contador).value,
            "almacen" : document.getElementById("nombreAlmacen" + contador).innerHTML,
            "fruta": document.getElementById("seleccionFruta")[document.getElementById("seleccionFruta").selectedIndex].text,
            "variedad": document.getElementById("nombreVariedad" + contador).innerHTML,
            "medida": document.getElementById("nombreMedida" + contador).innerHTML,
            "precio": document.getElementById("precio" + contador).innerHTML,
            "cantidad": document.getElementById("cantidad" + contador).value,
            "stock":stock
        };
        if (carrito.length > 0) {
            for (var j = 0; j < carrito.length && !salir; j++) {
                if (carrito[j].idDistribucion == anadir.idDistribucion) {
                    carrito[j].cantidad = anadir.cantidad;
                    salir = true;
                }
            }
            if (!salir) {
                carrito.push(anadir);
            }
        } else {
            carrito.push(anadir);
        }
        sessionStorage.setItem("carrito", JSON.stringify(carrito));
    };
    FRUTA.Carrito.comprar = function (finalizado) {
        var datosCompra = JSON.parse(sessionStorage.getItem("carrito"));
        var datosCliente = JSON.parse(sessionStorage.getItem("cliente"));
        var enviar = [];
        datosCompra.forEach(function (valor) {
            enviar.push({
                "idDistribucion": valor.idDistribucion,
                "idCliente": datosCliente.idCliente,
                "cantidad": valor.cantidad,
                "finalizado": finalizado
            });
        });
        var json = JSON.stringify(enviar);
        llamada = new ajax.CargadorContenidos("/ServletCompra", comprobarCompra, json);
    };

    //Esta funcion se dispara cuando se cierra el navegador
    window.onbeforeunload = function (e) {
        //Con valor 0 guardaremos los datos de la compra aunque no finalizaremos la misma
        FRUTA.Carrito.comprar(0);
    };

    FRUTA.Carrito.comprobarCompra = function () {
        var mensaje = llamada.req.responseText;
        if (mensaje.startsWith("Error")) {
            swal(mensaje, "", "error");
        } else {
            swal(mensaje, "", "success");
        }
    };

    // var borrarTabla = function (elemento) {
    //     while (elemento.hasChildNodes()) {
    //         elemento.removeChild(elemento.lastChild);
    //     }
    // };

    FRUTA.Carrito.borrarLinea = function (valorIdDistribucion) {
        var valoresCarrito = JSON.parse(sessionStorage.getItem("carrito"));
        var valorCarritoAux = [];
        valoresCarrito.forEach(function (linea) {
            if (linea.idDistribucion != valorIdDistribucion) {
                valorCarritoAux.push(linea);
            }
        });
        sessionStorage.removeItem("carrito");
        sessionStorage.setItem("carrito", JSON.stringify(valorCarritoAux));
        FRUTA.Cliente.rellenarCuerpoTabla();
    };
    FRUTA.Carrito.eliminarCarrito =function(idDistribucion) {
        var carritoAux = [];
        if (carrito.length > 0){
            carrito.forEach(function (valor) {
                if (valor.idDistribucion == idDistribucion){
                    valor.cantidad = 0;
                }
                if (valor.cantidad != 0){
                    carritoAux.push(valor);
                }
            })
        }
        carrito = carritoAux;
        sessionStorage.removeItem("carrito");
        sessionStorage.setItem("carrito", JSON.stringify(carrito));
        carrito = [];
    };
    var extraerDecimales = function (numero, decimales) {
        if (!decimales) decimales = 2;
        var d = Math.pow(10, decimales);
        return ((numero * d) / d).toFixed(decimales);
    };
    FRUTA.Carrito.capturadorEventos();
})(window);
