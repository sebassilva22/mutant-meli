# Mutant Meli Challenge

Este proyecto es desarrollado para una prueba solicitada por Mercado Libre Colombia, en el se evaluan si por medio de una cadena de DNA es o no mutante la persona.

## Lanzamiento de funci贸n de forma local 馃殌
Programa (Java, Golang, .Net, NodeJS) que cumpla con el m茅todo pedido por Magneto.

Para las pruebas locales de la funci贸n de selecci贸n de mutantes, se ejecutara por medio de una clase main en la cual encontrarn un Arreglo de String el cual pueden modificar para hacer el llamado.
![image](https://user-images.githubusercontent.com/50471336/126743844-758f18a8-7115-4e19-b777-b46a3f233157.png)

Para ejecutarlo por linea de comando se ejecuta 
```
mvn exec:java -Dexec.mainClass="com.meli.mutant.App" -Dexec.args="ACCCGA CAGTGC TTATGT AGAAGG CCCCTA TCACTG" 
```
Tener en cuenta que los argumentos van separados por espacios, el retorno de la funci贸n se veran en la consola.
![image](https://user-images.githubusercontent.com/50471336/126743989-a3e482b9-61ab-489d-bf2f-b71711011432.png)

## Pruebas de la funci贸n en ambiente cloud apigateway /mutant鉁掞笍

Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine, Amazon
AWS, etc), crear el servicio 鈥?/mutant/鈥? en donde se pueda detectar si un humano es mutante
enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente
formato:
```
POST 鈫? /mutant/
{
  "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] 
}
```
En caso de verificar un mutante, deber铆a devolver un HTTP 200-OK, en caso contrario un
403-Forbidden

Para probar el metodo de evaluaci贸n de un mutante, debemos ingresar en un sotware de peticiones rest Ej: postman o si se prefiere por un curl desde linea de comndos.
La url para realizar las peticiones es. https://9qod0pa9o6.execute-api.us-east-1.amazonaws.com/v1/mutant Es de metodo POST, el api por el momento no esta privado y tampoco solicita autorizaci贸n para efectos del challenge.

El body del petici贸n es el ya mencionado en el enunciado.

### Respuestas
Las psibles repuestas a la petici贸n son: 400, 403 y 200
* 400 : Cuando se env铆a un DNA invalido ya sea porque no contiene las letras ATGC o porque las cadenas de valor estan fuera de rango.
* 403 : Se lanza un forbiden cuando las cadenas de DNA son de humanos y de igual forma la regitra para postriores reportes.
* 200 : Ok, cuando la cadena cumple con el criterio para ser mutante. Este se registra en base de datos para posteriores reportes.

## Pruebas de la funci贸n en ambiente cloud apigateway /stats鉁掞笍
Anexar una base de datos, la cual guarde los ADN鈥檚 verificados con la API. Solo
1 registro por ADN.
Exponer un servicio extra 鈥?/stats鈥? que devuelva un Json con las estad铆sticas de las
verificaciones de ADN: {鈥渃ount_mutant_dna鈥?:40, 鈥渃ount_human_dna鈥?: 100, 鈥渞atio鈥?:0.4}
(ratio = mutants / humans)

Para traer las estadisticas, debemos ingresar en un sotware de peticiones rest Ej: postman o si se prefiere por un curl desde linea de comandos.
La url para realizar las peticiones es. https://9qod0pa9o6.execute-api.us-east-1.amazonaws.com/v1/stats Es de metodo GET, el api por el momento no esta privado y tampoco solicita autorizaci贸n para efectos del challenge.

No se requiere body ya que es GET

### Respuestas
Las psibles repuestas a la petici贸n son: 200

* 200 : Ok, cuando se consulta con exito las estadisticas.

## Reporte de cobertura > 80%
Para el reporte de cobertura, se ejecuta un 
```
mvn clean install
```
Esto genera una carpeta llamada **target** y dentro de esta una llamada **jacoco-report**
Se dirigen a la ruta dentro del pryecto que se genera **user_path/mutant-challenge/target/jacoco-report/index.html** All铆 se abre el index.html y se muestran el coverage desde el browser.

## Plus para levantamiento de ambiente propio 馃巵

Si se quiere hacer la construcci贸n de el apigateway, las lambdas y la base de datos en una cuenta propia de AWS, siga los siguientes pasos:

* Tenga instalado aws CLI
* Tenga instalado sam CLI
* sette sus credenciales de cuenta y la regi贸n por defecto. en los archivos credential y config de aws
* compile el proyecto: ``` mvn clean install ``` 
* Despues ejecute el comando de empaquetamiento  de sam: ``` sam package  --template-file template.yml --output-template-file packaged.yaml --s3-bucket coloque_aqui_su_bucket ```
* Ahora ejecute el comando de despliegue de sam : ``` sam deploy --template-file packaged.yaml --stack-name mutants --capabilities CAPABILITY_IAM ```

Listo, ahora los recursos que necesita para el programa los tiene en su cuenta de AWS.
