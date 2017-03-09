/**
 * Created by JAVI on 02/03/2017.
 */
// CLIENTE.namespace('CLIENTE.Principal');
(function (g) {

    var ajax = FRUTA.Ajax;
    var frutaAnterior;
    var nombreCliente = document.getElementById("cuadroBienvenida");
    var cliente = JSON.parse(sessionStorage.getItem("cliente"));
    var selection = document.getElementById("seleccionFruta");
    nombreCliente.innerHTML = "Bienvenido " + "<br>" + cliente.nombre;
    document.getElementById("salir").addEventListener("click", function () {
        sessionStorage.clear();
        window.location.href = "salirSesion.jsp";
    });

    selection.addEventListener("change", function () {
        var envio = {
            "accion": "rellenar",
            "fruta": selection.value
        };
        var json = JSON.stringify(envio);
        llamada = new ajax.CargadorContenidos("/Servlet", rellenar, json);

    }, true);
    function rellenar() {
        if (!(undefined == frutaAnterior)) {
            var eliminar = document.getElementsByClassName("claseEliminar");
            while(eliminar.length > 0){
                eliminar[0].parentNode.removeChild(eliminar[0]);
            }
        }
        var mensaje = JSON.parse(llamada.req.responseText);
        mensaje.forEach(function (linea) {
            var nodo = document.createElement("tr");
            nodo.setAttribute("class", "claseEliminar");
            nodo.innerHTML = "<td>" + linea.almacen + "</td><td>" + linea.variedad + "</td><td>" + linea.medida + "</td><td>" + linea.precio + "</td><td>" + linea.stock + "</td>";
            document.getElementById("tablaFruta").appendChild(nodo);
        });
        frutaAnterior = selection.value;
    }
})(window);
