package com.tienda.rutadelivery

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ApiService {

    private const val BASE_URL = "https://55pp8kzk7e.execute-api.us-east-1.amazonaws.com/v1"

    private fun request(method: String, path: String, body: JSONObject? = null): String? {
        return try {
            val fullUrl = "$BASE_URL$path"
            android.util.Log.d("API_TEST", "Llamando: $method $fullUrl")
            val url = URL(fullUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = method
            conn.setRequestProperty("Content-Type", "application/json")
            conn.connectTimeout = 10000
            conn.readTimeout = 10000
            if (body != null) {
                conn.doOutput = true
                conn.outputStream.write(body.toString().toByteArray())
            }
            val code = conn.responseCode
            android.util.Log.d("API_TEST", "Response code: $code")
            val stream = if (code in 200..299) conn.inputStream else conn.errorStream
            val result = stream.bufferedReader().readText()
            android.util.Log.d("API_TEST", "Response body: $result")
            result
        } catch (e: Exception) {
            android.util.Log.e("API_TEST", "Request error: ${e.message}")
            null
        }
    }

    private fun parseBody(res: String): String {
        return try {
            val wrapper = JSONObject(res)
            if (wrapper.has("body") && !wrapper.isNull("body")) {
                wrapper.getString("body")
            } else {
                res
            }
        } catch (e: Exception) {
            res
        }
    }

    suspend fun getRutas(): List<Ruta> = withContext(Dispatchers.IO) {
        try {
            val res = request("GET", "/rutas") ?: return@withContext emptyList()
            val body = parseBody(res)
            val arr = JSONArray(body)
            val rutas = mutableListOf<Ruta>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                rutas.add(Ruta(
                    id = obj.getInt("id"),
                    nombre = obj.getString("nombre"),
                    puntos = emptyList(),
                    distanciaKm = obj.getDouble("distanciaKm")
                ))
            }
            rutas
        } catch (e: Exception) {
            android.util.Log.e("API_TEST", "getRutas error: ${e.message}")
            emptyList()
        }
    }

    suspend fun crearRuta(ruta: Ruta): Int? = withContext(Dispatchers.IO) {
        try {
            val body = JSONObject()
            body.put("nombre", ruta.nombre)
            body.put("distanciaKm", ruta.distanciaKm)
            val paradasArr = JSONArray()
            ruta.puntos.forEach { p ->
                val pObj = JSONObject()
                pObj.put("nombre", p.nombre)
                pObj.put("direccion", p.direccion)
                pObj.put("lat", p.lat)
                pObj.put("lon", p.lon)
                paradasArr.put(pObj)
            }
            body.put("paradas", paradasArr)
            val res = request("POST", "/rutas", body) ?: return@withContext null
            val bodyStr = parseBody(res)
            val data = JSONObject(bodyStr)
            data.getInt("id")
        } catch (e: Exception) {
            android.util.Log.e("API_TEST", "crearRuta error: ${e.message}")
            null
        }
    }

    suspend fun eliminarRuta(id: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            request("DELETE", "/rutas/$id") ?: return@withContext false
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getParadas(rutaId: Int): List<Punto> = withContext(Dispatchers.IO) {
        try {
            val res = request("GET", "/paradas/ruta/$rutaId") ?: return@withContext emptyList()
            val body = parseBody(res)
            val arr = JSONArray(body)
            val paradas = mutableListOf<Punto>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                paradas.add(Punto(
                    id = obj.getInt("id"),
                    nombre = obj.getString("nombre"),
                    direccion = obj.getString("direccion"),
                    lat = obj.getDouble("lat"),
                    lon = obj.getDouble("lon")
                ))
            }
            paradas
        } catch (e: Exception) {
            android.util.Log.e("API_TEST", "getParadas error: ${e.message}")
            emptyList()
        }
    }

    suspend fun crearParada(parada: Punto, rutaId: Int, orden: Int): Int? = withContext(Dispatchers.IO) {
        try {
            val body = JSONObject()
            body.put("ruta_id", rutaId)
            body.put("nombre", parada.nombre)
            body.put("direccion", parada.direccion)
            body.put("lat", parada.lat)
            body.put("lon", parada.lon)
            body.put("orden", orden)
            val res = request("POST", "/paradas", body) ?: return@withContext null
            val bodyStr = parseBody(res)
            val data = JSONObject(bodyStr)
            data.getInt("id")
        } catch (e: Exception) {
            null
        }
    }

    suspend fun eliminarParada(id: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            request("DELETE", "/paradas/$id") ?: return@withContext false
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getPuntos(): List<Punto> = withContext(Dispatchers.IO) {
        try {
            val res = request("GET", "/puntos") ?: return@withContext emptyList()
            val body = parseBody(res)
            val arr = JSONArray(body)
            val puntos = mutableListOf<Punto>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                puntos.add(Punto(
                    id = obj.getInt("id"),
                    nombre = obj.getString("nombre"),
                    direccion = obj.getString("direccion"),
                    lat = obj.getDouble("lat"),
                    lon = obj.getDouble("lon")
                ))
            }
            puntos
        } catch (e: Exception) {
            android.util.Log.e("API_TEST", "getPuntos error: ${e.message}")
            emptyList()
        }
    }

    suspend fun crearPunto(punto: Punto): Int? = withContext(Dispatchers.IO) {
        try {
            val body = JSONObject()
            body.put("nombre", punto.nombre)
            body.put("direccion", punto.direccion)
            body.put("lat", punto.lat)
            body.put("lon", punto.lon)
            val res = request("POST", "/puntos", body) ?: return@withContext null
            val bodyStr = parseBody(res)
            val data = JSONObject(bodyStr)
            data.getInt("id")
        } catch (e: Exception) {
            null
        }
    }

    suspend fun eliminarPunto(id: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            request("DELETE", "/puntos/$id") ?: return@withContext false
            true
        } catch (e: Exception) {
            false
        }
    }
}