### Base de datos propuesta
#### Colaborador
- numeroPersonal
- nombre
- apellidoPaterno
- apellidoMaterno
- CURP
- correoElectronico
- password
- rol
- sucursal
- foto
- fotoBase64
- numeroLicencia
#### Rol
- idRol
- rol
#### Unidad
- marca
- modelo
- año
- VIN
- tipoPropulsionIdRef
- idInterno (año+primeros4VIN)
- estadoUnidadIdRef
- conductorDesignado(numero de licencia)
#### TipoPropulsion
- idPropulsion
- propulsion (gasolina, diesel, eléctrico)

#### LogUnidad
- VIN
- estadoUnidadIdRef
- descripcion
- fecha
- hora
#### EstadosUnidad
- idEstado
- Estado(activo, baja temporal, baja definitiva)
#### Sucursal
- idSucursal
- nombre
- estadoSucursal
- direccionSucursalIdRef
#### Direccion
- idDireccion
- linea1
- linea2
- linea3
- ciudad
- estado
- codigoPostal
- pais
- tipoIdRef 
#### TipoSucursal
- idTipo
- tipo (sucursal, cliente)
#### Cliente
- nombre
- apellidoPaterno
- apellidoMaterno
- direccionClienteIdRef
- telefono
- correoElectronico (identificador unico)
#### Envío
- numeroGuia (incremental, unico)
- correoElectronicoCliente
- nombreSujetoRecibe
- sucursalOrigenIdRef
- destinoIdRef
- costo
- estadoEnvioIdRef 
- conductorDesignado (numero de licencia)
#### EstadosEnvio
- idEstado
- Estado (recibido en sucursal, procesado, en transito, detenido, entregado o cancelado)
#### LogEnvio
- numeroGuia
- fecha
- hora
- colaboradorIdRef
- descripcionCambios
#### Paquete
- folio
- numeroGuiaAsignado
- descripcion
- peso
- alto
- ancho
- profundidad

### Lógica del negocio
#### Costo
Primero se calcula el costo adicional por paquete. 
1 paquete no tiene costo, 2 paquetes cuestan 50 mxn, 3 paquetes cuestan 80 mxn, 4 paquetes cuestan 110 y 5 paquetes o mas cuestan 150 mxn. El API externo recibe el CP origen y el CP destino y devuelve la distancia entre ellos en kilometros con 2 decimales. El costo por kilometro es de 4.00 mxn de 1 a 200 km, 3.00 mxn de 201 a 500 km, 2.00 mxn de 501 a 1000 km, 1.00 mxn de 1001 a 2000 km y 0.50 mxn para mas de 2000 km. El calculo de costos quedaria entonces como: (Distancia en kilometros x Costo por kilometro) + costo adicional por paquete. 

La peticion se hace con el metodo HTTP GET a :
http://sublimas.com.mx:8080/calculadora/api/envios/distancia/{cp_origen},{cp_destino}

Y regresa la respuesta en formato JSON:
{
"distanciaKM": 000.00,
"error":false,
"mensaje":"Distancia calculada correctamente entre CP 91014 y 91020"
}

Costo siempre > 0
Costo no cambia hasta que haya un cambio de dirección
Costo no puede cambiar si el envio esta en un estado final.

### Funciones
- Qué hace (ej. “crear envío”)
- Quién puede ejecutarla (admin, ejecutivo, conductor, ambos)
- Qué datos recibe (parámetros)
- Qué resultado (datos devueltos o solo confirmación)
- Si debe generar logs automáticamente
- Si hay reglas especiales (validaciones, estados permitidos, etc.)
Ejemplo:
> Función: Crear envío  
> Rol: Ejecutivo  
> Entradas: correo cliente, sucursal origen, destino, costo  
> Reglas: el envío inicia en estado “Recibido en sucursal”, debe crear log automáticamente  
> Salida: número de guía generado
##### Colaborador
Función: Registrar colaborador
Rol: Administrador
Entrada: nombre, apellido paterno, apellido materno, CURP, correo electronico, contraseña, rol, sucursal, fotografia, numero de licencia  
Reglas: Se permite dejar los campos de numero de licencia y fotografia vacios. Los demas campos son obligatorios. El CURP debe ser de 18 caracteres de longitud
Salida: confirmación del registro

Función: Editar colaborador
Rol: Administrador
Entrada: nombre, apellido paterno, apellido materno, CURP, correo electronico, contraseña, sucursal, fotografía, numero de licencia
Reglas: No se permite agregar el numero de licencia si el rol no es el de un conductor.
Salida: confirmación de la edición

Función: Eliminar colaborador
Rol: Administrador
Entrada: correo electronico, número de personal o CURP
Reglas: no se permite eliminar
Salida: confirmación de la eliminación

Función: Buscar colaborador
Rol: Administrador
Entrada: nombre, numero personal o rol.
Reglas: solo se permite buscar por nombre, numero personal o rol, no combinado.
Salida: una lista de colaboradores con el rol de la entrada, una lista de los colaboradores con el nombre de la entrada o un solo colaborador con el numero de personal de la entrada

---
##### Unidad
Funcion: Registrar unidad
Rol: Administrador
Entrada: marca, modelo, año, VIN. tipo de unidad (Gasolina, Diesel, Electrica e Hibrida, como viene en la tabla de tipos de propulsion)
Reglas: no se permiten duplicados de VIN, no se permite que un campo este vacio. El VIN y el año no deben tener espacios. El numero de identificacion interno se compone al registrar la unidad (agarra los primeros 4 caracteres del VIN y concatena el año. Por ejemplo, una unidad del 2025 con VIN GGXDM82A17A004352 tendria como numero de identificacion interna 2025GGXD). Las unidades siempre son registradas con el estado activo y usa la fecha y hora actuales para el log
Salida: confirmación del registro de la unidad

Funcion: Editar unidad
Rol: Administrador
Entrada: marca, modelo, año, tipo de unidad
Reglas: No se admiten entradas vacias o nulas. El VIN no se puede editar. El numero de identificacion interno es actualizado cuando el año del vehiculo cambia. No se permite editar una unidad con baja definitiva
Salida: confirmacion de la edicion

Función: Cambiar estado de la unidad
Rol: Administrador
Entrada: VIN o numero de identificación interno, id del estado, descripcion (motivo)
Reglas: No se admiten entradas vacias o nulas. Usa la fecha y hora actuales para el log. No se permite cambiar el estado de una unidad en baja definitiva
Salida: confirmación del cambio de estado

Funcion: Buscar unidad
Rol: Administrador
Entrada: VIN, numero de identificacion interno, o marca
Reglas: Solo se permite buscar por VIN, numero de identificacion interno o marca. Necesita por lo menos una de las opciones
Salida: una lista de unidades con la marca de la entrada, o una sola unidad con el VIN o numero de identificación interno de la entrada

Funcion: Asignar unidad
Rol: Administrador
Entrada: VIN o idInterno, numero de licencia
Reglas: Solo se permite usar el VIN o el numero de identificacion interno, no ambos. El colaborador debe existir, tener el rol de conductor y debe tener el numero de licencia. El conductor no debe tener una unidad asignada. La unidad para asignar no debe estar en baja definitiva. La unidad debe existir
Salida: Confirmacion de la asignacion de la unidad

Funcion:Desasignar unidad
Rol: Administrador
Entrada: VIN o idInterno
Reglas: Solo se permite usar el VIN o el numero de identificacion interno, no ambos. Debe existir la unidad
Salida: Confirmacion de la desasignación 

Funcion: Cambiar conductor designado de unidad
Rol: Administrador
Entrada: VIN o idInterno
Reglas:Solo permite usar el VIN o el numero de identificacion interno, no ambos. Debe existir el colaborador, tener el rol de conductor y debe tener el numero de licencia. Si algo falla rollback.
Salida: confirmacion de cambio de conductor



---
##### Sucursal
Funcion: Registrar sucursal
Rol: Administrador
Entrada: nombre de la sucursal, direccion(calle, numero, colonia, ciudad, estado, codigo postal, pais, tipo)
Reglas: El nombre de la sucursal es obligatorio. Cuando se añade una nueva sucursal su estatus será activo por defecto. La direccion sera de tipo sucursal y todos los campos deben estar llenos.
Salida: confirmación del registro.

Funcion: Editar sucursal
Rol: Administrador
Entrada:idSucursal, nombre de la sucursal, direccion(calle, numero, colonia, ciudad, estado, codigo postal, pais, tipo)
Reglas: Es obligatorio proporcionar el idSucursal para poder buscar la sucursal. Las entradas vacias se interpretan como que no se produce un cambio al original.
Salida: confirmacion de la edicion.

Funcion: Dar de baja sucursal
Rol: Administrador:
Entrada: idSucursal
Reglas: Es obligatorio proporcionar la id de la sucursal. No se puede dar de baja una sucursal que ya este de baja
Salida: Confirmacion de baja de sucursal

---
##### Cliente
Funcion: Registrar cliente
Rol: Administrador, Ejecutivo de tienda
Entrada: nombre, apellido paterno, apellido materno, direccion (calle, numero, colonia, ciudad, estado, codigo postal, pais), telefono, correo electronico
Reglas: Solo el campo de telefono y apellido materno es opcional, el correo electronico y el telefono es unico. El correo electronico debe tener formato de correo electronico (usuario@dominio.xxx). La direccion es de tipo cliente
Salida: confirmacion de registro

Funcion: Editar cliente
Rol: Administrador, Ejecutivo de tienda
Entrada: nombre, apellido paterno, apellido materno, direccion (calle, numero, colonia, ciudad, estado, codigo postal, pais), telefono, correo electronico
Reglas: Todos los campos son editables pero el telefono y correo electronico no puede estar repetido en la base de datos.
Salida: Confirmacion de edicion

Funcion: eliminar cliente
Rol: Administrador, Ejecutivo de tienda
Entrada: correo electronico o telefono
Reglas: Solo se acepta una entrada. Cuando se elimina un cliente tambien se elimina su direccion de la tabla de direcciones
Salida: Confirmacion de eliminacion

Funcion: buscar cliente
Rol: Administrador, Ejecutivo de tienda
Entrada: nombre, apellido paterno, apellido materno, numero de telefono o correo
Reglas: Solo se acepta una entrada.
Salida: Una lista de clientes con el mismo nombre o un solo cliente con el numero de telefono o correo electronico.

---
##### Conductor
Funcion: Editar conductor
Rol: conductor
Entrada: numero de personal, nombre, apellido paterno, apellido materno, CURP, correo electronico, contraseña, fotografía, numero de licencia
Reglas: Es obligatorio proporcionar el numero de licencia. Las entradas vacias se interpretan como que no se produce un cambio al original.
Salida: confirmacion de la edicion

---
##### Envios
Modulo de envios de ejecutivo
Funcion: Crear envio
Rol: Ejecutivo de tienda
Entrada: Nombre completo de quien recibe (nombre, apellido paterno y materno), sucursal de origen (id de referencia), direccion destino (calle, numero, colonia, codigo postal, ciudad, estado, pais), costo, conductor designado (numero de licencia), correo electronico cliente
Reglas: el numero de guia es generado automaticamente y unico por envio. El costo tiene que ser calculado basado en la distancia y tarifas. 
El estado inicial de envio sera "Recibido en sucursal" o '''Detenido' en caso que no haya un conductor disponible.
Salida: confirmacion del registro del envio

Funcion: Actualizar envio
Rol: Ejecutivo de tienda
Entrada: Nombre completo de quien recibe, sucursal de origen (id de referencia), direccion destino (calle, numero, colonia, codigo postal, ciudad, estado, pais),costo, conductor designado, correo electronico cliente
Reglas: El numero de guia no cambia, el correo electronico al que cambie estrictamente debe existir en la BD. La BD espera que se calculen los costos nuevamente si es que hubo cambios en las direcciones.
Salida: Confirmacion de la actualizacion

Funcion: Consultar envio
Rol: Ejecutivo de tienda
Entrada: numero de guia
Reglas:
Salida: datos del cliente (correo electronico, nombre completo), origen, destino, conductor designado, costo

Funcion: Actualizar estado
Rol: Ejecutivo de tienda
Entrada: numero de guia, estado nuevo, idcolaborador, descripcion de cambios
Reglas: El estado debe existir entre las opciones de la BD. Se debe almacenar el cambio del estado en un log del envio. El log del envio usa la fecha y la hora del momento por lo que no necesita la entrada del ejecutivo.
Salida: Confirmacion de la actualizacion

Modulo de envios de conductor
Funcion: Ver lista de envios asignados
Rol: Conductor
Entrada: numero de licencia
Reglas: No se muestran envios que ya hayan sido entregados
Salida: Lista de envios asignados a un conductor con la licencia proporcionada. La informacion mostrada solo es el numero de guia, la direccion de destino y el estado actual.

Funcion: Ver detalle del envio
Rol: Conductor
Entrada: numero de guia
Reglas:
Salida: Sucursal de origen, nombre quien recibe, direccion destino,lista de paquetes contenidos, estado del envio, contacto del cliente (nombre completo, telefono y correo electronico).

Función: Cambiar estado sobre marcha
Rol: Conductor
Entrada: numero de guía, estado nuevo, idColaborador, descripcion de cambios.
Reglas: El estado debe existir entre las opciones de la BD. Conductor solo puede cambiar el estado a: En transito, Detenido, Entregado, Cancelado. Se debe almacenar el cambio del estado en un log del envio. El log del envio usa la fecha y la hora del momento por lo que no necesita la entrada del conductor.
Salida: confirmación de que el estado fue cambiado

Modulo de envios cliente
Funcion: consultar rastreo de un envio
Rol: Cliente
Entrada: numero de guia
Reglas: Ninguna, de hecho ni es necesario hacer algun tipo de inicio de sesion. Cualquiera que tenga el numero de guia del envio puede checar la información de rastreo. Se espera que el numero de guia solo lo tenga el cliente, el ejecutivo de tienda y el conductor. Si el numero de guia es filtrado la responsabilidad recae en en los colaboradores que fueron registrados en el log del envio o en el cliente. 
Salida: Numero de guia, nombre sujeto que recibe, sucursal origen, pais destino, estado actual, costo, lista de paquetes del envio. Un log del historial del envio.

### Definicion de Estados
Recibido en sucursal = Todos los paquetes de sucursal de un envio ya han sido recibidos por el conductor en la sucursal de origen.

Procesado = Cualquier problema que haya causado el detenimiento de un envio ya fue procesado esta a la espera de volver a transito. En la descripcion del log se especifica como se resolvió.

En transito = De momento el envio esta en transito

Detenido = Es generico, puede significar que no haya un conductor, un problema logistico como que este en aduana, o que el intento de entrega sea fallido. Esto se especifica en mas profundidad en la descripcion del log.

Entregado = Se ha confirmado que el envio llego al destino y fue recibido. Este es un estado final

Cancelado = El envio ha sido cancelado. La razon se especifica en el log. Este es un estado final

Cuando se llega a un estado final no es posible alterar los datos del envio pero todavia es posible leerlos.

Transiciones:
Un envio puede empezar con estado "Detenido" o "Recibido en sucursal".

"Recibido en sucursal" debe pasar a -> "En transito".
"Detenido" debe pasar a -> "Procesado".
"Procesado" debe pasar a -> "En transito".
"En transito" puede pasar a -> "Entregado" o "Detenido".

"Entregado" no cambia de estado.
"Cancelado" no cambia de estado.

Exceptuando los estados finales, todos los demas estados pueden pasar a "Cancelado"

**TTL de envios cancelados**:
El tiempo de vida de la vista de los envios cancelados sera de 7 dias. Esta regla solo aplica para el conductor. Esto quiere decir que un conductor solo podra ver en su lista de envios asignados, envios cancelados de hasta 7 dias.

**Manejo de envios cancelados**:
Cuando el envio es cancelado se maneja de distinta forma dependiendo de quien haga la cancelacion.
Cuando un cliente decide cancelar su envio tiene que hacerlo a traves del ejecutivo de la tienda. En este caso el log finaliza abrubtamente en la decision hasta donde quedo. Por ejemplo, si el envio estaba en transito, la siguiente entrada en el log sera el de estado cancelado y no se podra modificar.
Un conductor puede decidir cancelar el envio si considera que es imposible realizar el envio (cuando es peligroso o la direccion no existe ya), o si ha sido autorizado a cancelarlo (quizas despues de varios intentos fallidos). Cuando esto pasa el log finaliza abruptamente hasta donde quedo, ahora con el estado de cancelado y sin poder modificarse nuevamente. La ultima entrada que aparecera en el log es la cancelacion

# Anexo
## Problemática
La empresa “Packet-World” líder en el ramo de envíos por paquetería necesita una solución
que permita la gestión completa de envíos de paquetes desde una aplicación de escritorio,
con monitoreo y visualización desde una aplicación web y desde una aplicación móvil para
conductores.

## Descripción de casos de uso
### Actor: Administrador

**CU-ADM-01 Registrar colaborador**  
Propósito: Dar de alta un colaborador en el sistema.  
Precondiciones: El administrador está autenticado. El rol existe.  
Flujo principal:

1. El administrador captura los datos del colaborador.
    
2. El sistema valida campos obligatorios, unicidad de CURP y licencia.
    
3. El sistema registra al colaborador activo.  
    Resultado: Colaborador registrado y disponible para operación.  
    Excepciones: CURP o correo duplicado, rol inválido.
    

---

**CU-ADM-02 Editar colaborador**  
Propósito: Actualizar datos de un colaborador existente.  
Precondiciones: El colaborador existe.  
Flujo principal:

1. El administrador selecciona un colaborador.
    
2. Modifica datos permitidos.
    
3. El sistema valida reglas de rol/licencia.  
    Resultado: Datos actualizados.  
    Excepciones: Intento de asignar licencia a un rol no conductor.
    

---

**CU-ADM-03 Desactivar colaborador**  
Propósito: Evitar que un colaborador siga operando sin borrar historial.  
Precondiciones: Colaborador existente.  
Resultado: Colaborador marcado como inactivo.

---

**CU-ADM-04 Eliminar colaborador**  
Propósito: Eliminar físicamente un colaborador cuando el cliente lo exige.  
Precondiciones: Colaborador existente.  
Resultado: Datos eliminados o anonimizados manteniendo integridad histórica.

---

**CU-ADM-05 Registrar unidad**  
Propósito: Registrar una nueva unidad vehicular.  
Precondiciones: VIN único.  
Flujo principal:

1. Administrador captura datos de la unidad.
    
2. El sistema genera idInterno.
    
3. Se asigna estado “Activo”.
    
4. Se genera LogUnidad inicial.  
    Resultado: Unidad disponible para asignación.
    

---

**CU-ADM-06 Editar unidad**  
Propósito: Modificar datos de una unidad activa.  
Restricción: No aplica a unidades en baja definitiva.  
Resultado: Datos actualizados y log registrado.

---

**CU-ADM-07 Cambiar estado de unidad**  
Propósito: Cambiar el estado operativo de una unidad.  
Resultado: Estado actualizado y registro histórico generado.

---

### Actor: Ejecutivo de tienda

**CU-EJT-01 Crear envío**  
Propósito: Registrar un nuevo envío en el sistema.  
Precondiciones: Cliente existente.  
Flujo principal:

1. Ejecutivo captura datos del envío.
    
2. El sistema crea la dirección destino.
    
3. Se asigna estado inicial (“Recibido en sucursal” o “Detenido”).
    
4. Se genera número de guía.
    
5. Se crea log inicial.  
    Resultado: Envío creado.  
    Excepciones: Cliente inexistente, datos incompletos.
    

---

**CU-EJT-02 Agregar paquetes a envío**  
Propósito: Asociar paquetes físicos al envío.  
Precondiciones: Envío no finalizado.  
Resultado: Paquetes registrados correctamente.

---

**CU-EJT-03 Actualizar envío**  
Propósito: Modificar datos logísticos del envío.  
Restricciones:

- No se puede modificar si está Entregado o Cancelado.
    
- No se cambia conductor ni cliente.  
    Resultado: Dirección y costo actualizados.
    

---

**CU-EJT-04 Cambiar estado del envío**  
Propósito: Gestionar el avance del envío.  
Resultado: Estado actualizado y log generado.

---

**CU-EJT-05 Cancelar envío**  
Propósito: Cancelar un envío por solicitud del cliente.  
Resultado: Envío cancelado y bloqueado para cambios.

---

### Actor: Conductor

**CU-CON-01 Ver envíos asignados**  
Propósito: Consultar envíos a su cargo.  
Restricciones:

- No se muestran entregados.
    
- Cancelados solo hasta 7 días.  
    Resultado: Lista de envíos operativos.
    

---

**CU-CON-02 Ver detalle del envío**  
Propósito: Consultar información completa para entrega.  
Resultado: Detalle del envío, paquetes y contacto del cliente.

---

**CU-CON-03 Cambiar estado del envío en ruta**  
Propósito: Reportar avance o problemas del envío.  
Restricciones:

- Solo ciertos estados permitidos.  
    Resultado: Estado actualizado y log registrado.
    

---

**CU-CON-04 Cancelar envío**  
Propósito: Cancelar un envío cuando es imposible entregarlo.  
Resultado: Envío cancelado definitivamente.

---
### Actor: Cliente

**CU-CLI-01 Consultar rastreo de envío**  
Propósito: Conocer el estado del envío.  
Precondiciones: Tener el número de guía.  
Flujo principal:

1. Cliente ingresa número de guía.
    
2. El sistema muestra estado actual, costo, paquetes e historial.  
    Resultado: Información de rastreo visible.  
    Restricciones: No requiere autenticación.

