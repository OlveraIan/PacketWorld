package uv.tc.packetworldmovil.interfaz

import uv.tc.packetworldmovil.pojo.Envio
import uv.tc.packetworldmovil.pojo.Paquete

interface NotificarClic {

    fun seleccionarItem(posicion : Int, envio: Envio)
    fun seleccionarItem(posicion : Int, paquete: Paquete)

}