/**
 * Created by JAVI on 02/03/2017.
 */
FRUTA.namespace('FRUTA.clienteprincipal');
(function () {
    // window.addEventListener("beforeunload", function(e){
    //    localStorage.removeItem("cliente");
    //    // window.location.href="killer.jsp";
    // }, false);
    var nombreCliente = document.getElementById("cuadroBienvenida");
    var cliente = JSON.parse(sessionStorage.getItem("cliente"));
    nombreCliente.innerHTML = "Bienvenido " + cliente.nombre;
    swal("Bienvenido "+ cliente.nombre,"", "success");
    document.getElementById("salir").addEventListener("click",function () {
        sessionStorage.clear();
        window.location.href="killer.jsp";
    });
})(window);
