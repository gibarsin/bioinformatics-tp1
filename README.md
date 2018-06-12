## Resumen
Resolución de ejercicio 1 y 2 del TP Especial para la materia _Introducción a la Bioinformática_.
## Requisitos
- Java 10
- Maven 3
- Emboss [Ejercicio 5]

## Cómo compilar el código
Para compilar el código correr en la línea de comandos sobre la raíz del proyecto:
```bash
mvn clean package
```
El programa _bio.jar_ se encuentra en el directorio _target_ generado en la compilación.

## Cómo ejecutar el código
Para ejecutar el código, correr en la línea de comandos sobre la raíz del proyecto:
```bash
java -jar target/bio.jar <exercise> <arguments>
```
en donde <exercise\> es el número de ejercicio y <arguments\> son los argumentos dependiendo del ejercicio.

## Cómo ejecutar cada ejercicio
Ejercicio 1) Ejecutar:
```bash
java -jar target/bio.jar 1 <genBankPath>
```
en donde <genBankPath> es el path al archivo de tipo genbank. La salida se encontrará en el mismo directorio con el 
mismo nombre y extensión cambiada a _.fas_.

Ejercicio 2) Ejecutar:
```bash
java -jar target/bio.jar 2 <fastaPath> <isLocal>
```
en donde <fastaPath\> es el path al archivo de tipo fasta que contiene la secuencia correcta de proteínas, de todas las 
secuencias obtenidas del ejercicio 1; <isLocal\> es el booleano que decide si ejecuta la consulta sobre una base de 
datos local (opción no soportada actualmente) o sobre la base de datos púlica de BLAST. El resultado se guardará en un
archivo con nombre _blast.out_ en el mismo directorio que el archivo de entrada.

Ejercicio 4) En caso de querer preguntar por un pattern en particular, ejecutar:
```bash
java -jar target/bio.jar 4 <fastaPath> a <pattern>
```
En caso de querer consultar por todos los patterns únicos, ejecutar:
```bash
java -jar target/bio.jar 4 <fastaPath> b
```

Ejercicio 5)
En la raíz del proyecto dirigirse al directorio `Exercise5` que dentro contiene el script en bash 
`Exercise5.sh`. Ejecutar:
```bash
./Exercise5.sh <fastaPath>
```
en donde <fastaPath\> es la secuencia de nucleótidos de la cuál se desea obtener los ORF y los dominios.
La salida se encontrará en los archivos cuyo nombre es el mismo nombre que el del archivo pero con 
extensión _.orf_ y _.patmatmotifs_.