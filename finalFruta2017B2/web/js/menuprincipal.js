/**
 * Created by Luciano on 21/02/2017.
 */
FRUTA.namespace('FRUTA.MenuPrincipal');
(function (g) {
    'use strict';
    var validar = FRUTA.Validar;
    var ajax = FRUTA.Ajax;
    var llamada;

    FRUTA.MenuPrincipal.capturarEventos = function () {

        document.getElementById("cuadro01").style.display = "none";
        document.getElementById("cuadro_login").style.display = "none";
        document.getElementById("publico_registrar").addEventListener("click", FRUTA.MenuPrincipal.registrar);
        document.getElementById("publico_entrar").addEventListener("click", FRUTA.MenuPrincipal.login);
    };

    FRUTA.MenuPrincipal.login = function (event) {

        event.preventDefault();
        document.getElementById("main").style.display = "none";
        document.getElementById("cuadro01").style.display = "none";
        document.getElementById("cuadro_login").style.display = "block";
        document.getElementById("boton_login").style.display = "block";
        document.getElementById("boton_login").addEventListener("click",FRUTA.MenuPrincipal.validarLogin);
    };

    FRUTA.MenuPrincipal.registrar = function (event) {

        event.preventDefault();

        document.getElementById("main").style.display = "none";
        document.getElementById("cuadro01").style.display = "block";
        document.getElementById("error01").style.display = "none";
        document.getElementById("registrarse").style.display = "none";
        document.getElementById("cuadro_login").style.display = "none";

        FRUTA.MenuPrincipal.validarRegistro();
    };

    FRUTA.MenuPrincipal.validarRegistro = function () {

        var nombre = document.getElementById("registro_nombre");
        var apellidos = document.getElementById("registro_apellidos");
        var usuario = document.getElementById("registro_usuario");
        var password = document.getElementById("registro_password");

        nombre.addEventListener("keyup", FRUTA.MenuPrincipal.validarNombreCliente, false);
        apellidos.addEventListener("keyup", FRUTA.MenuPrincipal.validarApellidosCliente, false);
        usuario.addEventListener("keyup", FRUTA.MenuPrincipal.validarUsuarioCliente, false);
        password.addEventListener("keyup", FRUTA.MenuPrincipal.validarContraseniaCliente, false);
    };

    function comprobarValido(nodoEnviar) {

        if (document.getElementById("registro_nombre").style.background.substr(0, FRUTA.Validar.colorvalido.length)
            == FRUTA.Validar.colorvalido &&
            document.getElementById("registro_apellidos").style.background.substr(0, FRUTA.Validar.colorvalido.length)
            == FRUTA.Validar.colorvalido &&
            document.getElementById("registro_usuario").style.background.substr(0, FRUTA.Validar.colorvalido.length)
            == FRUTA.Validar.colorvalido
            && document.getElementById("registro_password").style.background.substr(0, FRUTA.Validar.colorvalido.length)
            == FRUTA.Validar.colorvalido) {

            var boton = document.getElementById(nodoEnviar);
            boton.style.display = "";
            boton.addEventListener("click", FRUTA.MenuPrincipal.enviarRegistro);
        }
    }

    FRUTA.MenuPrincipal.validarLogin = function () {
        var usuario = document.getElementById("login_usuario").value;
        var password = document.getElementById("login_password").value;
        if (usuario.length > 0 && password.length > 0){
            var envio = {
                "accion" : "login",
                "usuario" : usuario,
                "password" : hex_md5(password)
            };
            var json = JSON.stringify(envio);
            llamada = new ajax.CargadorContenidos("/Servlet", FRUTA.MenuPrincipal.Acceder, json);
        }
        else {
            swal("Debes rellenar todos los campos", "", "error");
        }
    };
    FRUTA.MenuPrincipal.Acceder  = function () {
        var mensaje = this.req.getResponseHeader("mensaje");
        if(mensaje.startsWith("Error")){
            swal(mensaje, "", "error");
        }else {
            var cliente=JSON.parse(mensaje);
            sessionStorage.setItem("cliente",mensaje);
            mensaje="Bienvenido "+cliente.nombre;
            swal(mensaje, "", "success");
            location.reload();
        }
    };
    
    FRUTA.MenuPrincipal.validarNombreCliente = function () {
        var parametros = {
            nodo: this.id,
            nodoEnviar: "registrarse",
            nodoError: "error01",
            valorMinimo: 3,
            valorMaximo: 30
        };
        validar.cadenaConespacio(parametros);
        comprobarValido(parametros.nodoEnviar);
    };

    FRUTA.MenuPrincipal.validarApellidosCliente = function () {
        var parametros = {
            nodo: this.id,
            nodoEnviar: "registrarse",
            nodoError: "error01",
            valorMinimo: 10,
            valorMaximo: 100
        };
        validar.cadenaConespacio(parametros);
        comprobarValido(parametros.nodoEnviar);
    };

    FRUTA.MenuPrincipal.validarUsuarioCliente = function () {
        var parametros = {
            nodo: this.id,
            nodoEnviar: "registrarse",
            nodoError: "error01",
            valorMinimo: 5,
            valorMaximo: 50
        };
        validar.cadenaNumeroSinespacio(parametros);
        comprobarValido(parametros.nodoEnviar);
    };
    FRUTA.MenuPrincipal.validarContraseniaCliente = function () {
        var parametros = {
            nodo: this.id,
            nodoEnviar: "registrarse",
            nodoError: "error01",
            valorMinimo: 5,
            valorMaximo: 100
        };
        validar.cadenaNumeroSinespacio(parametros);
        comprobarValido(parametros.nodoEnviar);
    };

    FRUTA.MenuPrincipal.enviarRegistro = function () {
        var envio = {
            "accion" : "registro",
            "nombreCliente": document.getElementById("registro_nombre").value,
            "apellidosCliente": document.getElementById("registro_apellidos").value,
            "usuarioCliente": document.getElementById("registro_usuario").value,
            "passwordCliente": hex_md5(document.getElementById("registro_password").value)
        };
        var json = JSON.stringify(envio);
        llamada = new ajax.CargadorContenidos("/Servlet", FRUTA.MenuPrincipal.VerificarInsercion, json);
    };

    FRUTA.MenuPrincipal.VerificarInsercion = function () {
        var mensaje = this.req.getResponseHeader("mensaje");
        FRUTA.MenuPrincipal.mostrarMensaje(mensaje);
    };

    FRUTA.MenuPrincipal.mostrarMensaje = function (mensaje) {
        var opcion = mensaje.substring(0,7).trim();
        if ( opcion == "Cliente") {
            swal(mensaje, "", "success");
        } else {
            swal(mensaje, "", "error");
        }
    };
    FRUTA.MenuPrincipal.capturarEventos();


})(window);