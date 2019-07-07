//-------------------------------------Criação do Escudo------------------------------------------------
function ladoEsquerdoEscudo(ctx, color)
{
        ctx.fillStyle = color;

        ctx.beginPath();
            ctx.moveTo(0.500, 0.0035);
            ctx.quadraticCurveTo(0.453, 0.075, 0.351, 0.02);
            ctx.quadraticCurveTo(0.250, 0.49, 0.500, 0.753);
            ctx.lineTo(0.500, 0.0035);
        ctx.closePath();

        ctx.fill();
}

function ladoDireitoEscudo(ctx, color)
{
    ctx.translate(1, 0); //Coloca o centro do referencial no sitio pretendido
    ctx.scale(-1, 1);
    ladoEsquerdoEscudo(ctx, color);
}

//Escudo criado a partir de
//De translação de metade
function escudo(ctx, color)
{
    ladoEsquerdoEscudo(ctx, color);
    ladoDireitoEscudo(ctx, color);
}

//----------------------------------------Uso do Escudo------------------------------------------------
function escudoExterior(ctx)
{
    //Gradiente
    var color = ctx.createLinearGradient(0.294,0.0149, 0.672, 0.752);

    color.addColorStop(0.05, "#B1A6A2");
    color.addColorStop(0.2, "#D8D1CB");
    color.addColorStop(0.6, "#B1A6A2");

    ctx.save();
        ctx.scale(1540, 872);
        escudo(ctx, color);
    ctx.restore();
}

//Funciona como contorno
function escudoIntermédio(ctx)
{

    //Gradiente
    var color = ctx.createLinearGradient(0.294,0.0149, 0.672, 0.752);

    color.addColorStop(0.4, "#B2A3A3");
    color.addColorStop(0.6, "#EFE5E7");
    
    var color =  color;

    ctx.save();
        ctx.translate(79.5, 35);
        ctx.scale(1381, 784);
        escudo(ctx, color);
    ctx.restore();
}

//Ok
function escudoInterior(ctx)
{
    //Gradiente 
    var color = ctx.createRadialGradient(0.550, 0.371, 0.13, 0.510, 0.371, 0.3);

    color.addColorStop(0, "#3E7ECF");
    color.addColorStop(0.9, "#013662");

    ctx.save();
        ctx.translate(115.5, 49);
        ctx.scale(1309, 743);
        escudo(ctx, color);
    ctx.restore();
}
//----------------------------------------Brazao----------------------------------------------
//Cria um dos diamantes
function diamante(ctx)
{
    //Gradiente
    var color = ctx.createLinearGradient(0.48,0.31, 0.52, 0.41);

    color.addColorStop(0.4, "#AFA5A0");
    color.addColorStop(0.8, "#f2f2f2");

    ctx.fillStyle = color;
    ctx.lineWidth = 0.001;
    ctx.strokeStyle = "white";

    ctx.save();
        ctx.beginPath();
            ctx.moveTo(0.500, 0.334);
            ctx.lineTo(0.492, 0.350);
            ctx.lineTo(0.492, 0.389);
            ctx.lineTo(0.500, 0.405);
            ctx.lineTo(0.507, 0.389);
            ctx.lineTo(0.507, 0.350);
            ctx.lineTo(0.500, 0.334);
            ctx.fill();
            ctx.stroke();
        ctx.closePath()
    ctx.restore();
}

//Cria um dos castelos
function castelo(ctx)
{

    //Gradiente
    var color = ctx.createLinearGradient(0.46,0.12, 0.54, 0.2);

    color.addColorStop(0.4, "#f2f2f2");
    color.addColorStop(0.8, "#AFA5A0");
    

    ctx.fillStyle = color;
    ctx.lineWidth = 0.0007;
    ctx.strokeStyle = "grey";

    ctx.save();
        ctx.beginPath();
            ctx.moveTo(0.491, 0.147);
            ctx.ellipse(0.501, 0.147, 0.0035, 0.008, 0, -Math.PI, 0);
            ctx.lineTo(0.511, 0.147);
            ctx.lineTo(0.508, 0.154);
            ctx.lineTo(0.512, 0.182);
            ctx.lineTo(0.490, 0.182);
            ctx.lineTo(0.494, 0.154);
            ctx.lineTo(0.491, 0.147);
            ctx.fill();
            ctx.stroke();
        ctx.closePath();
    ctx.restore();
}

function brazaoForma(ctx)
{

    //Gradiente
    var color = ctx.createLinearGradient(0.38, 0.08, 0.59, 0.661);

    color.addColorStop(0.2, "#B1A198");
    color.addColorStop(0.9, "#E8E4E1");
    
    ctx.fillStyle = color;
    ctx.strokeStyle = "white";
    ctx.lineWidth = 0.003;
    ctx.lineCap = 'round';

    ctx.beginPath();
        ctx.moveTo(0.500, 0.108);
        ctx.quadraticCurveTo(0.462, 0.174, 0.406, 0.171);
        ctx.lineTo(0.406, 0.466);
        ctx.quadraticCurveTo(0.437, 0.568, 0.500, 0.640);
        ctx.quadraticCurveTo(0.563, 0.568, 0.594, 0.466);
        ctx.lineTo(0.594, 0.171);
        ctx.quadraticCurveTo(0.538, 0.174, 0.500, 0.108);
        //Delimita a parte interior do escudo
        ctx.moveTo(0.500, 0.190);
        ctx.lineTo(0.445, 0.221);
        ctx.lineTo(0.445, 0.455);
        ctx.lineTo(0.500, 0.553),
        ctx.lineTo(0.555, 0.455);
        ctx.lineTo(0.555, 0.221);
        ctx.lineTo(0.500, 0.190); 

        ctx.fill('evenodd');
        ctx.stroke();
    ctx.closePath();
}

//Cria os varios diamantes aplicando transformacoes
function mapaDeDiamantes(ctx)
{
    /*
        Disposicao dos diamantes
            x3
        x4  x1  x5
            x2
    */ 

    //x1
    ctx.save();
        ctx.scale(1540, 872);
        diamante(ctx);
    ctx.restore();

    //x2
    ctx.save();
        ctx.translate(0, -75);
        ctx.scale(1540, 872);
        diamante(ctx);
    ctx.restore();

    //X3
    ctx.save();
        ctx.translate(0, 75);
        ctx.scale(1540, 872);
        diamante(ctx);
    ctx.restore();

    //x4
    ctx.save();
        ctx.translate(-40, 0);
        ctx.scale(1540, 872);
        diamante(ctx);
    ctx.restore();

    //x5
    ctx.save();
        ctx.translate(40, 0);
        ctx.scale(1540, 872);
        diamante(ctx);
    ctx.restore();
}

//Cria os varios castelos aplicando transformacoes
function mapaDeCastelos(ctx)
{
    /*
    DISPOSIÇÂO DOS CASTELOS
            x1
        x2      x3
        x4      x5      
          x6  x7
    */

    //x1
    ctx.save();
        ctx.scale(1540, 872);
        castelo(ctx);
    ctx.restore();

    //x2
    ctx.save();
        ctx.translate(-115, 50);
        ctx.scale(1540, 872);
        castelo(ctx);
    ctx.restore();
    
    //x3
    ctx.save();
        ctx.translate(115, 50);
        ctx.scale(1540, 872);
        castelo(ctx);
    ctx.restore();

    //x4
    ctx.save();
        ctx.translate(-115, 180);
        ctx.scale(1540, 872);
        castelo(ctx);
    ctx.restore();

    //x5
    ctx.save();
        ctx.translate(115, 180);
        ctx.scale(1540, 872);
        castelo(ctx);
    ctx.restore();

    //x6
    ctx.save();
        ctx.rotate(-45*Math.PI/180);
        ctx.translate(-600, 680);
        ctx.scale(1540, 872);
        castelo(ctx);
    ctx.restore();

    //x7
    ctx.save();
        ctx.rotate(45*Math.PI/180);
        ctx.translate(145, -410);
        ctx.scale(1540, 872);
        castelo(ctx);
    ctx.restore();
}

//Brazao com todos os elementos que lhe pertencem
function brazaoCompleto(ctx)
{
    ctx.save();
        ctx.scale(1540, 872);
        brazaoForma(ctx);
    ctx.restore();

    mapaDeCastelos(ctx);

    mapaDeDiamantes(ctx);
}
//--------------------------------------------Banner---------------------------------------------
function fita(ctx)
{

    //Gradiente
    var color = ctx.createLinearGradient(0.36,0.5, 0.628, 0.65);

    color.addColorStop(0.3, "#B1A6A2");
    color.addColorStop(0.5, "#D8D1CB");
    color.addColorStop(0.7, "#B1A6A2");
    color.addColorStop(0.9, "#D8D1CB");

    ctx.fillStyle = color;
    ctx.lineWidth = 0.001;
    ctx.strokeStyle = "white";

    ctx.save();
        ctx.beginPath();
            ctx.moveTo(0.366, 0.589);
            ctx.quadraticCurveTo(0.392, 0.646, 0.378, 0.670);
            ctx.quadraticCurveTo(0.418, 0.719, 0.5, 0.67);
            ctx.quadraticCurveTo(0.590, 0.639, 0.622, 0.666);
            ctx.quadraticCurveTo(0.606, 0.621, 0.636, 0.586);
            ctx.quadraticCurveTo(0.594, 0.554, 0.5, 0.602);
            ctx.quadraticCurveTo(0.401, 0.634, 0.366, 0.589);
            ctx.fill();
            ctx.stroke();
        ctx.closePath();
    ctx.restore();
}

function data(ctx)
{
    ctx.save();
        var distance = 40;
        ctx.font = ' 60px "Baker"';
        ctx.fillStyle = '#000000';
        ctx.textAlign = "center";   
    
        ctx.fillText("1", 705, 585);
        ctx.fillText("9", 705+distance, 580);
        ctx.fillText("2", 705+distance*2, 575);
        ctx.fillText("5", 705+distance*3, 570);
    ctx.restore();
}

function banner(ctx)
{
    ctx.save();
        ctx.scale(1540, 872);
        fita(ctx);
    ctx.restore();

    data(ctx);
}

//----------------------------------------------------TEXT-------------------------------------------
function PORTVGÁLIA(ctx)
{
    ctx.save();
        ctx.font = ' 195px "Baker"';
        ctx.fillStyle = '#000000';
        ctx.textAlign = "left";   
        ctx.fillText("PORTVGÁLIA", 280, 840);
    ctx.restore();
}
//----------------------------------------------------------------------------------------------------
function main()
{
    var canvas = document.getElementById("canvas");
    var c2d = canvas.getContext("2d");

    c2d.fillStyle = "#F7F6F4";
    c2d.fillRect(10, 0, 1540, 872);

    escudoExterior(c2d);
    escudoIntermédio(c2d);
    escudoInterior(c2d);
    brazaoCompleto(c2d);
    banner(c2d);
    PORTVGÁLIA(c2d);
}
