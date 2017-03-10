/**
 * Created by Yo on 02/03/2017.
 */
// CLIENTE.namespace('CLIENTE.Principal');
var ajax = FRUTA.Ajax;
var llamada;
var carrito = [];
(function (g) {
    var nombreCliente = document.getElementById("cuadroBienvenida");
    var cliente = JSON.parse(sessionStorage.getItem("cliente"));
    nombreCliente.innerHTML = "Bienvenido " + "<br>" + cliente.nombre;
    var opcion = document.getElementById('seleccionFruta');
    // document.getElementById("carritoAnadir").addEventListener("click", anadirCarrito);
    opcion.addEventListener("change", function () {
        var envio = {
            codFruta: opcion.value
        };
        var json = JSON.stringify(envio);
        llamada = new ajax.CargadorContenidos("/ServletAlmacenStock", crearTabla, json);

    }, true);


})(window);


function crearTabla() {
    var mensaje = llamada.req.responseText;
    if (mensaje.startsWith("Error")) {
        swal(mensaje, "", "error");
    } else {
        var almacenStock = JSON.parse(mensaje);
        var tabla = document.getElementById("tablaFruta");
        while (tabla.hasChildNodes()) {
            tabla.removeChild(tabla.lastChild);
        }
        var contador = 0;
        almacenStock.forEach(function (valor) {
            var nodo = document.createElement("div");
            nodo.setAttribute("class", "table-row item");
            nodo.innerHTML = "<img src=''>" +
                "<div class ='info' id='info'"+contador+">" + "<input type='hidden' value='" + valor.idDistribucion + "' id='idDistribucion" + contador + "'>" +
                "<tr>" +
                "<td><div id='nombreAlmacen" + contador + "'>" + valor.nombreAlmacen + "</div></td>" +
                "<td><div id='nombreVariedad" + contador + "'>" + valor.nombreVariedad + "</div></td>" +
                "<td><div id='nombreMedida" + contador + "'>" + valor.nombreMedida + "</div></td>" +
                "<td><div id='precio" + contador + "'>" +extraerDecimales(valor.precio,2)+ "&#8364</div></td>" +
                "<td><div id='stock" + contador + "'>" + valor.stock + "</div></td>" +
                "<td><div><a  style='cursor:pointer' id ='menos" + contador + "'><img src=\"img/menos.png\"></a>" +
                "<input type=\"number\" value='0' min ='0' max='" + valor.stock + "' id='cantidad" + contador + "'>" +
                "<a style='cursor:pointer' id='mas" + contador + "' ><img src=\"img/mas.png\"></a>" +
                "<a style='cursor:pointer' id='borrar" + contador + "'><img src=\"img/papelera.png\"></a></div></td>" +
                "</tr>" +
                "</div>";

            document.getElementById("tablaFruta").appendChild(nodo);

            document.getElementById("cantidad" + contador).addEventListener("change", function () {
                comprobarCantidad(this.id.substring(8, contador.length), valor.stock);
            });
            document.getElementById("mas" + contador).addEventListener("click", function () {
                aumentar(this.id.substring(3, contador.length), valor.stock);
            });
            document.getElementById("menos" + contador).addEventListener("click", function () {
                disminuir(this.id.substring(5, contador.length), valor.stock);
            });
            document.getElementById("borrar" + contador).addEventListener("click", function () {
                eliminar(this.id.substring(6, contador.length));
            });
            contador++;
        });

    }
}

function comprobarCantidad(contador, stock) {

    if (document.getElementById("cantidad" + contador).value < 1 || document.getElementById("cantidad" + contador).value > stock) {
        document.getElementById("cantidad" + contador).value = 0;
        borrarCarrito(document.getElementById("idDistribucion" + contador).value);
    } else {
        anadirCarrito(contador);
    }
}
function eliminar(contador) {
    document.getElementById("cantidad" + contador).value = 0;
    borrarCarrito(document.getElementById("idDistribucion" + contador).value);
}
function aumentar(contador, stock) {
    document.getElementById("cantidad" + contador).value = parseInt(document.getElementById("cantidad" + contador).value) + 1;
    comprobarCantidad(contador, stock);
}

function disminuir(contador, stock) {
    document.getElementById("cantidad" + contador).value = parseInt(document.getElementById("cantidad" + contador).value) - 1;
    console.log(document.getElementById("cantidad" + contador).value);
    comprobarCantidad(contador, stock);
}


document.getElementById("salir").addEventListener("click", function () {
    sessionStorage.clear();
    window.location.href = "salirSesion.jsp";
});

function anadirCarrito(contador) {
    var salir = false;
    var precio=document.getElementById("precio" + contador).textContent;
    var anadir = {
        "idDistribucion": document.getElementById("idDistribucion" + contador).value,
        "almacen":document.getElementById("nombreAlmacen" + contador).textContent,
        "fruta":document.getElementById("seleccionFruta")[document.getElementById("seleccionFruta").selectedIndex].text,
        "variedad":document.getElementById("nombreVariedad" + contador).textContent,
        "medida":document.getElementById("nombreMedida" + contador).textContent,
        "precio":precio.substring(0,precio.length-1),
        "cantidad": document.getElementById("cantidad" + contador).value
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
}

function borrarCarrito(idDistribucion) {

    var carritoAux = [];
    if (carrito.length > 0) {
        for (var j = 0; j < carrito.length; j++) {
            if (carrito[j].idDistribucion != idDistribucion) {
                carritoAux.push(carrito[j]);
            }
        }
    }
    carrito = carritoAux;
    sessionStorage.removeItem("carrito");
    sessionStorage.setItem("carrito", JSON.stringify(carrito));
}
function extraerDecimales(numero,decimales) {
    if (!decimales) decimales = 2;
    var d = Math.pow(10, decimales);
    return ((numero * d) / d).toFixed(decimales);
}