/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 * AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package net.daw.service.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specific.implementation.OpcionBean;
import net.daw.bean.specific.implementation.PreguntaBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.specific.implementation.PreguntaDao;
import net.daw.helper.statics.AppConfigurationHelper;
import static net.daw.helper.statics.AppConfigurationHelper.getSourceConnection;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.JsonMessage;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author juliomiguel
 */
public class PreguntaService extends TableServiceGenImpl {

    public PreguntaService(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String get() throws Exception {

        int id = ParameterCook.prepareId(oRequest);

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);

        PreguntaBean oPreguntaBean = new PreguntaBean();
        oPreguntaBean.setId(id);

        oPreguntaBean = oPreguntaDao.get(oPreguntaBean, 1);

        Gson gson = AppConfigurationHelper.getGson();
        String data = gson.toJson(oPreguntaBean);

        return "{\"status\":200,\"message\":" + data + "}";
    }
    
    
    public String getopciones() throws Exception {

        int id = ParameterCook.prepareId(oRequest);

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);

        OpcionBean oOpcionBean = new OpcionBean();
        oOpcionBean.setId_pregunta(id);

        ArrayList<OpcionBean> alOpcionBean = new ArrayList<OpcionBean>();
        alOpcionBean = oPreguntaDao.getOpciones(oOpcionBean, 1);

        Gson gson = AppConfigurationHelper.getGson();
        String data = gson.toJson(alOpcionBean);

        return "{\"status\":200,\"message\":" + data + "}";
    }
    
    

    @Override
    public String getall() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
        ArrayList<PreguntaBean> alPreguntaBean = new ArrayList<PreguntaBean>();
        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
        HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);

        alPreguntaBean = oPreguntaDao.getAll(alFilterBeanHelper, hmOrder);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy");
        Gson gson = gsonBuilder.create();
        String data = "{\"status\":200,\"message\":" + gson.toJson(alPreguntaBean) + "}";

        return data;
    }

    @Override
    public String getcount() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        PreguntaDao oProfesorDao = new PreguntaDao(oConnection);

        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
        int counter = oProfesorDao.getCount(alFilterBeanHelper/*alFilter*/);

        String data = "{\"status\":200,\"message\":" + Integer.toString(counter) + "}";

        return data;
    }

    @Override
    public String set() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
        PreguntaBean oPreguntaBean = new PreguntaBean();
        String json = ParameterCook.prepareJson(oRequest);
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").excludeFieldsWithoutExposeAnnotation().create();
        /*oProfesorBean.setId(2);
         oProfesorBean.setNombre("julio");
         oProfesorBean.setEstado("the best");*/
        oPreguntaBean = gson.fromJson(json, PreguntaBean.class);
        oPreguntaBean = oPreguntaDao.set(oPreguntaBean);
        Map<String, String> data = new HashMap<>();
        data.put("status", "200");
        data.put("message", Integer.toString(oPreguntaBean.getId()));
        String resultado = gson.toJson(data);
        return resultado;
    }

    @Override
    public String remove() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        int id = ParameterCook.prepareId(oRequest);
        PreguntaDao oProfesorDao = new PreguntaDao(oConnection);

        PreguntaBean oProfesorBean = new PreguntaBean();
        oProfesorBean.setId(id);
        oProfesorDao.remove(oProfesorBean);

        Map<String, String> data = new HashMap<>();
        data.put("status", "200");
        data.put("message", "se ha eliminado la pregunta con id= " + ((Integer) id).toString());
        Gson gson = new Gson();
        String resultado = gson.toJson(data);
        return resultado;
    }

    @Override
    public String getpage() throws Exception {

        Connection oConnection = null;

        oConnection = new BoneConnectionPoolImpl().newConnection();

        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);

        ArrayList<PreguntaBean> oPreguntaArray = new ArrayList<>();

        //Obtenemos parámetros con el ParameterCook
        int rpp = ParameterCook.prepareRpp(oRequest);

        int page = ParameterCook.preparePage(oRequest);

        //ArrayList para sacar los filtros
        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);

        //HashMap para sacar el orden
        HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);

        //Asignamos getPage(con sus parámetros) al ArrayList oProfesorArray
        oPreguntaArray = oPreguntaDao.getPage(rpp, page, alFilterBeanHelper, hmOrder);

        //Creamos el Json para mostrarlo en pantalla
        Gson oGson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return "{\"status\":200,\"message\":" + oGson.toJson(oPreguntaArray) + "}";

    }

    @Override
    public String getmetainformation() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
        Gson oGson = new GsonBuilder().setDateFormat("dd/MM/yyyy").excludeFieldsWithoutExposeAnnotation().create();
        return "{\"status\":200,\"message\":" + oGson.toJson(oPreguntaDao.getmetainformation()) + "}";

    }

    @Override
    public String getpages() throws Exception {
        if (this.checkpermission("getpages")) {
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            String strResult = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
                int intRpp = ParameterCook.prepareRpp(oRequest);
                ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
                strResult = ((Integer) oPreguntaDao.getPages(intRpp, alFilterBeanHelper)).toString();
            } catch (Exception ex) {
                oConnection.rollback();
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return JsonMessage.getJsonMsg("200", strResult);
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String getaggregateviewone() throws Exception {

        if (this.checkpermission("getaggregateviewone")) {
            String data = null;
            try {
                String meta = this.getmetainformation();
                String one = this.get();
                data = "{"
                        + "\"meta\":" + meta
                        + ",\"bean\":" + one
                        + "}";
                data = JsonMessage.getJson("200", data);
                return data;
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAggregateViewOne ERROR: " + ex.getMessage()));
            }
            return data;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String getaggregateviewsome() throws Exception {
        if (this.checkpermission("getaggregateviewsome")) {
            String data = null;
            try {
                String meta = this.getmetainformation();
                String page = this.getpage();
                String pages = this.getpages();
                String registers = this.getcount();
                data = "{"
                        + "\"meta\":" + meta
                        + ",\"page\":" + page
                        + ",\"pages\":" + pages
                        + ",\"registers\":" + registers
                        + "}";
                data = JsonMessage.getJson("200", data);
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAggregateViewSome ERROR: " + ex.getMessage()));
            }
            return data;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String getaggregateviewall() throws Exception {
        if (this.checkpermission("getaggregateviewall")) {
            String data = null;
            try {
                String meta = this.getmetainformation();
                String all = this.getall();
                String registers = this.getcount();
                data = "{"
                        + "\"meta\":" + meta
                        + ",\"page\":" + all
                        + ",\"registers\":" + registers
                        + "}";
                data = JsonMessage.getJson("200", data);
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAggregateViewAll ERROR: " + ex.getMessage()));
            }
            return data;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

}
