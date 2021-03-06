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
function fCuestionarioRoutes() {
    var icon = '<i class="fa fa-question fa-5x"></i>';
    var fillDocumentoPageHeader = _.partial(html.getPageHeader, icon, 'Cuestionario', _);
    var strClass = 'cuestionario';
    var header = $('#broth_panel_heading');
    var place = $('#broth_content');

    Path.map("#/" + strClass + "/view/:id").to(function () {
        //INSERTAMOS EL CSS
        $('<link href="css/main/cuestyle.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        //Cambiar cabecera por titulo de cuestionario
        header.empty().append(fillDocumentoPageHeader('View'));
        var strParam = parameter.getUrlObjectFromUrlString(this.params['url']);
        ausiasFLOW.reset();
        ausiasFLOW.initialize(cuestionarioView, place, strClass, 'cuestionarioview', strParam);
        return false;
    });
    
    //--------------------------------------------------------------------------    
    Path.map("#/" + strClass + "/list(/:url)").to(function () {
        $('<link href="css/main/cuestyle.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        header.empty().append(fillDocumentoPageHeader('List'));
        var strParam = parameter.getUrlObjectFromUrlString(this.params['url']);
        ausiasFLOW.reset();
        ausiasFLOW.initialize(cuestionarioList, place, strClass, 'cuestionariolist', strParam);
        return false;
    });

}