# Examen PSP – Cliente/Servidor con hilos: «La Taquilla»

## Enunciado

Una sala de conciertos vende **20 entradas**. Hay que programar un servidor de taquilla que atienda
a varios compradores a la vez y un programa cliente que simule esos compradores.

La comunicación se hace con `ObjectOutputStream` / `ObjectInputStream`, enviando únicamente objetos `String`.
El **servidor** se organiza en hilos y recursos y el **cliente** en **MVC**:

```
sorpresa/
├── servidor/
│   ├── ServidorTaquilla      (main: acepta conexiones)
│   ├── hilos/                GestorCliente
│   └── recursos/             Taquilla
└── cliente/
    ├── modelo/               Comprador
    ├── vista/                VistaCliente
    └── controlador/          ClienteTaquilla, SimuladorClientes
```

- **Cliente – Modelo:** los datos y sus reglas (no usa sockets ni `System.out`).
- **Cliente – Vista:** única clase que escribe por consola.
- **Cliente – Controlador:** sockets, hilos y protocolo; usa el modelo y la vista.

### Protocolo

El cliente envía una petición y el servidor responde siempre con una línea:

| Petición del cliente | Respuesta del servidor |
|----------------------|------------------------|
| `STOCK`              | `STOCK:<n>` (entradas que quedan) |
| `COMPRAR:<n>`        | `OK:<n>` si hay suficientes, o `ERROR:No hay suficientes entradas` |
| `COMPRAR:<texto o n<=0>` | `ERROR:Cantidad no válida` |
| `SALIR`              | `AGUR` y se cierra la conexión |
| cualquier otra cosa  | `ERROR:Comando desconocido` |

## Partes

### Recurso compartido: clase `Taquilla`
Paquete `sorpresa.servidor.recursos`.
- Debe ser **segura ante acceso concurrente** (nunca se pueden vender más de 20 entradas).
- Atributo con las entradas disponibles (inicialmente 20, recibido por constructor).
- `getEntradasDisponibles()` y `getVentasTotales()`.
- `boolean comprar(int cantidad)`: descuenta las entradas y devuelve `true`; si no hay suficientes devuelve `false`.

### Servidor multihilo
Puerto **5050**.
- `ServidorTaquilla` (paquete `sorpresa.servidor`, con `main`): crea **una única** `Taquilla` y acepta conexiones
  indefinidamente. Por cada cliente lanza un **hilo nuevo** (no debe atender a un cliente a la vez).
- `GestorCliente` (paquete `sorpresa.servidor.hilos`, `Runnable`): recibe el `Socket` y la `Taquilla`, atiende las
  peticiones del protocolo en bucle hasta `SALIR` o hasta que el cliente se desconecte, y cierra los recursos
  correctamente.
- El servidor muestra por consola el nombre del hilo y la petición recibida.

### Cliente en MVC
- `ClienteTaquilla` (controlador, `Runnable`): se conecta al servidor y, en bucle, pide `STOCK`;
  si quedan entradas, pide `COMPRAR:<1 a 3 al azar>` y muestra la respuesta; si no quedan, envía `SALIR`.
  Al final muestra cuántas entradas ha comprado en total.