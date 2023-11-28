package com.example.electrorui.db

import com.example.electrorui.db.dao.ConteoRapidoCompDao
import com.example.electrorui.db.dao.DatosRegistroDao
import com.example.electrorui.db.dao.FuerzaDao
import com.example.electrorui.db.dao.MensajeDao
import com.example.electrorui.db.dao.MunicipiosDao
import com.example.electrorui.db.dao.PaisDao
import com.example.electrorui.db.dao.PuntoIDao
import com.example.electrorui.db.dao.RegistroFamiliasDao
import com.example.electrorui.db.dao.RegistroNombresDao
import com.example.electrorui.db.dao.RescateCompDao
import com.example.electrorui.db.dao.RescateDao
import com.example.electrorui.db.dao.UsuarioDao
import com.example.electrorui.db.entityModel.FuerzaEntity
import com.example.electrorui.db.entityModel.PaisEntity
import com.example.electrorui.db.entityModel.RegistroFamiliasEntity
import com.example.electrorui.db.entityModel.RegistroNombresEntity
import com.example.electrorui.db.entityModel.UsuarioEntity
import com.example.electrorui.db.entityModel.toDB
import com.example.electrorui.db.entityModel.toFuerzaDB
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.db.entityModel.toUpdateDB
import com.example.electrorui.networkApi.model.PuntosInterModel
import com.example.electrorui.networkApi.model.RescateCompModel
import com.example.electrorui.networkApi.model.toAPI
import com.example.electrorui.networkApi.model.toApi
import com.example.electrorui.networkApi.retrofitService
import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.example.electrorui.usecase.model.Fuerza
import com.example.electrorui.usecase.model.Iso
import com.example.electrorui.usecase.model.Mensaje
import com.example.electrorui.usecase.model.Municipios
import com.example.electrorui.usecase.model.NumerosFam
import com.example.electrorui.usecase.model.Pais
import com.example.electrorui.usecase.model.PinFamilias
import com.example.electrorui.usecase.model.PinNacionalidad
import com.example.electrorui.usecase.model.PuntosInter
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RegistroNombres
import com.example.electrorui.usecase.model.Rescate
import com.example.electrorui.usecase.model.RescateComp
import com.example.electrorui.usecase.model.RespuestaA
import com.example.electrorui.usecase.model.User
import com.example.electrorui.usecase.model.toPaisUC
import com.example.electrorui.usecase.model.toUC
import com.example.electrorui.usecase.model.toUpdate
import com.example.electrorui.usecase.model.toUser
import javax.inject.Inject

class RepositoryApp @Inject constructor(
    private val api : retrofitService,
    private val usuarioDao: UsuarioDao,
    private val datosRegistroDao: DatosRegistroDao,
    private val paisDao: PaisDao,
    private val fuerzaDao: FuerzaDao,
    private val rescateDao: RescateDao,
    private val municipiosDao: MunicipiosDao,
    private val registroNombresDao: RegistroNombresDao,
    private val registroFamiliasDao: RegistroFamiliasDao,
    private val puntoIDao: PuntoIDao,
    private val rescateCompDao: RescateCompDao,
    private val mensajeDao: MensajeDao,
    private val conteoRapidoCompDao: ConteoRapidoCompDao,
) {
//  Obtener datos del Api de Retrofit
    suspend fun getUserFromApi(user : User) : User{
        val response = api.verifyUser(user.toApi())
        return response.toUser()
    }
    suspend fun getAllPaisesFromApi() : List<Pais> {
        val response = api.getPaises()
        return response.map { it.toPaisUC() }
    }
    suspend fun getAllFuerzaFromApi() : List<Fuerza> {
        val response = api.getFuerza()
        return response.map { it.toUC() }
    }
    suspend fun getAllMunicipiosFromApi() : List<Municipios>{
        val response = api.getMunicipios()
        return response.map { it.toUC() }
    }
    suspend fun getAllPuntosInterFromApi() : List<PuntosInter>{
        val response = api.getPuntosInter()
        return response.map { it.toUC() }
    }

    suspend fun insertRescatesFromApi(registros : List<RescateComp>): RespuestaA{
        return api.setRescates(registros.map { it.toAPI() } )
    }

    suspend fun insertConteosFromApi(registros : List<ConteoRapidoComp>) : RespuestaA{
       return api.setConteos(registros.map { it.toAPI() } )
    }
//    ---------------------------------------------
//--------- Obtener datos de la Base de Datos DB ---------------------
//    ---------------------------------------------
    suspend fun getTotalRegistrosNombresFromDB() : Int {
        return registroNombresDao.getTotal()
    }
    suspend fun getTotalConteoRapidoDB() : Int{
        return datosRegistroDao.getTotal()
    }
    suspend fun getTotalRegistrosFamiliasFromDB() : Int {
        return registroFamiliasDao.getTotal()
    }
    suspend fun getAllUsersFromDB() : List<User>{
        val response = usuarioDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllPaisesFromDB() : List<Pais>{
        val response = paisDao.getAll()
        return response.map { it.toPaisUC() }
    }
    suspend fun getAllFuerzaFromDB() : List<Fuerza>{
        val response = fuerzaDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllOrsDB() : List<String>{
        val response = municipiosDao.getOrs()
        return response
    }
    suspend fun getAllDatosRegistroDB() : List<RegistroNacionalidad>{
        val response = datosRegistroDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllRegistroNombresDB() : List<RegistroNombres>{
        val response = registroNombresDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllRegistrosFamiliasDB() : List<RegistroFamilias>{
        val response = registroFamiliasDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllRescatesFromDB() : List<Rescate>{
        val response = rescateDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllMunicipiosFromDB() : List<Municipios>{
        val response = municipiosDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllByIsoFromDB() : List<Iso>{
        val response = registroNombresDao.getIsoCount()
        return response.map { it.toUC() }
    }
    suspend fun getAllPuntosInterFromDB() : List<PuntosInter>{
        val response = puntoIDao.getAll()
        return response.map { it.toUC() }
    }
    suspend fun getAllRescateDataFromDB() : List<Rescate>{
        val response = rescateDao.getAll()
        return response.map { it.toUC() }
    }

    suspend fun getAllMensajesFromDB() : List<Mensaje>{
        val response = mensajeDao.getAll()
        return response.map { it.toUC() }
    }

    suspend fun getDataPinNombresFromDB() : List<PinNacionalidad>{
         return registroNombresDao.getDataForPin()
    }

    suspend fun getDataPinFamiliasFromDB() : List<PinFamilias>{
        return registroFamiliasDao.getDataForPin()
    }

    suspend fun getAllDataConteoRapidoFromDB() : List<ConteoRapidoComp>{
        val response = conteoRapidoCompDao.getAll()
        return response.map { it.toUC() }
    }

    suspend fun getAllRescateCompletoFromDB() : List<RescateComp>{
        val response = rescateCompDao.getAll()
        return response.map { it.toUC() }
    }

    suspend fun getFamiliarByIdFromDB( id : Int) : RegistroFamilias {
        val response = registroFamiliasDao.getById(id)
        return response.toUC()
    }

    suspend fun getNombreByIdFromDB( id : Int) : RegistroNombres {
        val response = registroNombresDao.getById(id)
        return response.toUC()
    }

    suspend fun getAllByNacionalidadFromDB( nacionalidad : String) : List<RegistroNombres>{
        val response = registroNombresDao.getByNacionalidad(nacionalidad)
        return response.map { it.toUC() }
    }
    suspend fun getFuerzaByOrDB(oficinaR : String) : List<Fuerza>{
        val response = fuerzaDao.getFuerzaByOr(oficinaR)
        return response.map { it.toUC() }
    }
    suspend fun getMunByOrDB(oficinaR : String) : List<Municipios>{
        val response = municipiosDao.getMunByOr(oficinaR)
        return response.map { it.toUC() }
    }
    suspend fun getFamiliaByNumDB(numeroFamilia : Int) : List<RegistroFamilias>{
        val response = registroFamiliasDao.getFamiliaByNum(numeroFamilia)
        return response.map { it.toUC() }
    }
    suspend fun getNumFamiliasDB() : List<NumerosFam> {
        return registroFamiliasDao.getNumFamilias()
    }
    suspend fun getPuntosAeropuertoDB() : List<String> {
        return puntoIDao.getByAeropuerto()
    }
    suspend fun getPuntosTerrestresDB() : List<String> {
        return puntoIDao.getByTerrestres()
    }
    suspend fun getRegistrosByIdFromDB( id : Int) : RegistroNacionalidad{
        return datosRegistroDao.getById(id).toUpdate()
    }
    suspend fun insertUser(usuario : List<UsuarioEntity>) {
        usuarioDao.insert(usuario)
    }

    suspend fun insertRegistroNombre( ingresoDato : List<RegistroNombres>){
        registroNombresDao.insert(ingresoDato.map { it.toDB() })
    }

    suspend fun insertPaises(paises : List<Pais>) {
        paisDao.insert(paises.map { it.toPaisDB() })
    }

    suspend fun insertFuerzas(fuerzas : List<Fuerza>) {
        fuerzaDao.insert(fuerzas.map { it.toFuerzaDB() })
    }

    suspend fun insertRescates(fuerzas : List<Fuerza>) {
        fuerzaDao.insert(fuerzas.map { it.toFuerzaDB() })
    }

    suspend fun insertMunicipios(municipios : List<Municipios>) {
        municipiosDao.insert(municipios.map { it.toDB() })
    }

    suspend fun insertRegistroNacionalidad(registros : List<RegistroNacionalidad>) {
        datosRegistroDao.insert(registros.map { it.toDB() })
    }

    suspend fun insertRegistroFamilia(registro : List<RegistroFamilias>) {
        registroFamiliasDao.insert(registro.map { it.toDB() })
    }
    suspend fun insertPuntosI(registro : List<PuntosInter>) {
        puntoIDao.insert(registro.map { it.toDB() })
    }

    suspend fun insertRescatesCompletos(registro : List<RescateComp>) {
        rescateCompDao.insert(registro.map { it.toDB() })
    }
    suspend fun insertMensajeToDB( mensaje : List<Mensaje>) {
        mensajeDao.insert(mensaje.map { it.toDB() })
    }

    suspend fun insertDataConteoRapidoToDB( registros : List<ConteoRapidoComp>) {
        conteoRapidoCompDao.insert(registros.map { it.toDB() })
    }

    suspend fun insertTipoRescateToDB(registro : List<Rescate>) {
        rescateDao.insert(registro.map { it.toDB() })
    }

    suspend fun deleteAllUsers() {
        usuarioDao.deleteAll()
    }

    suspend fun deleteAllFuerza() {
        fuerzaDao.deleteAll()
    }

    suspend fun deleteAllPaises() {
        paisDao.deleteAll()
    }

    suspend fun deleteAllMunicipios() {
        municipiosDao.deleteAll()
    }

    suspend fun deleteAllRegistrosNacionalidad() {
        datosRegistroDao.deleteAll()
    }

    suspend fun deleteAllRegistrosNombres() {
        registroNombresDao.deleteAll()
    }

    suspend fun deleteAllRegistrosFamilias() {
        registroFamiliasDao.deleteAll()
    }
    suspend fun deleteAllPuntosI() {
        puntoIDao.deleteAll()
    }
    suspend fun deleteRescatesCompletos() {
        rescateCompDao.deleteAll()
    }
    suspend fun deleteAllDataConteoRapidoFromDB() {
        conteoRapidoCompDao.deleteAll()
    }
    suspend fun deleteAllTipoRescateFromDB() {
        rescateDao.deleteAll()
    }
    suspend fun deleteAllRescateCompletoFromDB() {
        rescateCompDao.deleteAll()
    }
    suspend fun deleteRegistroNombreIdFromDB(item : RegistroNombres ) {
        registroNombresDao.deleteEntry(item.toUpdateDB())
    }
    suspend fun deleteRegistroFamiliasIdFromDB(item : RegistroFamilias ) {
        registroFamiliasDao.deleteEntry(item.toUpdateDB())
    }
    suspend fun deleteConteoRByIdFromDB(item : RegistroNacionalidad) {
        datosRegistroDao.deleteEntry(item.toUpdateDB())
    }
    suspend fun updateNombres(registro : RegistroNombres) {
        registroNombresDao.update(registro.toUpdateDB())
    }
    suspend fun updateFamiliar(registro : RegistroFamilias) {
        registroFamiliasDao.update(registro.toUpdateDB())
    }
    suspend fun updateRescateCompleto(registro : RescateComp) {
        rescateCompDao.update(registro.toDB())
    }
    suspend fun updateConteoR(registro : RegistroNacionalidad) {
        datosRegistroDao.update(registro.toUpdateDB())
    }
}