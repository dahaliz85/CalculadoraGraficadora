package com.biomath3d.controller;

import com.biomath3d.math.form.FormularioCalculoVectorial;
import com.biomath3d.math.form.FormularioDerivadas;
import com.biomath3d.math.parser.DetectorVariables;
import com.biomath3d.math.parser.EvaluadorExpresion;
import com.biomath3d.math.parser.Token;
import com.biomath3d.math.parser.Tokenizador;
import javafx.scene.control.TextArea;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Controlador encargado de gestionar, ramificar y documentar el desarrollo aritmético
 * y analítico de las operaciones seleccionadas en la UI de BioMath 3D.
 */
public class ProcessController {

    public void registrarProcesoEcuacion(String operacion, String cadenaCompleta, TextArea txtResultados) {
        if (txtResultados == null) return;

        try {
            // 1. Correr el detector inteligente para inferir las variables
            DetectorVariables detector = new DetectorVariables();
            detector.analizarPropuesta(cadenaCompleta);

            String ecuacionLimpia = detector.getExpresionLimpia();
            List<String> vars = detector.getVariablesInferidas();

            // 2. Tokenizar y pasar a Postfijo (Dijkstra)
            List<Token> tokensInfijos = Tokenizador.tokenizar(ecuacionLimpia, detector);
            List<Token> tokensPostfijos = EvaluadorExpresion.convertirAPostfijo(tokensInfijos);

            // Punto de prueba estándar para el análisis numérico
            double valX = 1.0;
            double valY = 1.0;
            double valorConstanteGenerica = 2.0;

            // Definimos la superficie f(x,y) como una función matemática ejecutable en Java
            // Pasamos los 6 parámetros requeridos (incluyendo el detector)
            BiFunction<Double, Double, Double> superficief = (x, y) ->
                    EvaluadorExpresion.evaluarPostfijo(tokensPostfijos, x, y, 0.0, valorConstanteGenerica, detector);

            // 3. Construcción del encabezado de inferencia
            StringBuilder reporte = new StringBuilder();
            reporte.append("====================================================\n");
            reporte.append("         SISTEMA DE INFERENCIA ALGEBRAICA           \n");
            reporte.append("====================================================\n");
            reporte.append("Variables Detectadas: ").append(vars.toString()).append("\n");
            reporte.append("Operación Solicitada: ").append(operacion).append("\n");
            reporte.append("Expresión Base:      f").append(vars.toString().replace("[", "(").replace("]", ")")).append(" = ").append(ecuacionLimpia).append("\n\n");

            // 4. Ramificar según la funcionalidad seleccionada en el ComboBox
            switch (operacion) {
                case "Generar Malla Superficie 3D":
                    reporte.append("-> MÓDULO DE GRAFICACIÓN ACTIVADO\n");
                    reporte.append("Evaluando punto muestra para verificar consistencia...\n");
                    // Se añade el detector al método que genera la bitácora paso a paso
                    String desarrolloBase = EvaluadorExpresion.obtenerDesarrolloPasoAPaso(tokensPostfijos, valX, valY, 0.0, valorConstanteGenerica, detector);
                    reporte.append(desarrolloBase);
                    break;

                case "Calcular Gradiente ∇f":
                    reporte.append("-> MÓDULO DE CÁLCULO VECTORIAL: GRADIENTE\n");
                    reporte.append("Fórmula: ∇f(x,y) = (∂f/∂x)i + (∂f/∂y)j\n");
                    reporte.append("----------------------------------------------------\n");

                    double[] gradiente = FormularioCalculoVectorial.calcularGradiente(superficief, valX, valY);

                    reporte.append(String.format("Evaluando en el punto de prueba P(%.2f, %.2f):\n", valX, valY));
                    reporte.append(String.format("Paso 01: Componente i (∂f/∂x) = %.4f\n", gradiente[0]));
                    reporte.append(String.format("Paso 02: Componente j (∂f/∂y) = %.4f\n", gradiente[1]));
                    reporte.append("----------------------------------------------------\n");
                    reporte.append(String.format("VECTOR GRADIENTE RESULTANTE: ∇f = %.4fi + %.4fj\n", gradiente[0], gradiente[1]));
                    reporte.append("====================================================\n");
                    break;

                case "Calcular Plano Tangente y Normal":
                    reporte.append("-> MÓDULO GEOMÉTRICO: PLANO TANGENTE\n");
                    reporte.append("Fórmula: z - z0 = fx(x0,y0)(x - x0) + fy(x0,y0)(y - y0)\n");
                    reporte.append("----------------------------------------------------\n");

                    double[] componentesPlano = FormularioCalculoVectorial.calcularComponentesPlanoTangente(superficief, valX, valY);
                    double z0 = componentesPlano[0];
                    double fx = componentesPlano[1];
                    double fy = componentesPlano[2];

                    reporte.append(String.format("Punto de contacto: P0(%.2f, %.2f, %.4f)\n", valX, valY, z0));
                    reporte.append(String.format("Pendiente en x (fx): %.4f\n", fx));
                    reporte.append(String.format("Pendiente en y (fy): %.4f\n", fy));
                    reporte.append("----------------------------------------------------\n");
                    reporte.append(String.format("ECUACIÓN DEL PLANO TANGENTE:\nz = %.4f + (%.4f)(x - %.2f) + (%.4f)(y - %.2f)\n", z0, fx, valX, fy, valY));
                    reporte.append(String.format("\nVECTOR NORMAL A LA SUPERFICIE:\nN = %.4fi + %.4fj - 1.0000k\n", fx, fy));
                    reporte.append("====================================================\n");
                    break;

                case "Calcular Divergencia (Campos)":
                    reporte.append("-> MÓDULO DE CAMPOS VECTORIALES: DIVERGENCIA (∇ · F)\n");
                    reporte.append("Fórmula: div F = ∂P/∂x + ∂Q/∂y + ∂R/∂z\n");
                    reporte.append("----------------------------------------------------\n");

                    String[] componentesDiv = ecuacionLimpia.split(",");

                    // Se corrigen las funciones lambda agregando el detector al final para evitar desfasamientos
                    BiFunction<Double, Double, Double> superficiefP = (x, y) ->
                            EvaluadorExpresion.evaluarPostfijo(tokensPostfijos, x, y, 0.0, valorConstanteGenerica, detector);
                    BiFunction<Double, Double, Double> superficiefQ = (x, y) ->
                            componentesDiv.length > 1 ? superficiefP.apply(x, y) : 0.0;
                    BiFunction<Double, Double, Double> superficiefR = (x, y) -> 0.0;

                    double divergencia = FormularioCalculoVectorial.calcularDivergencia(superficiefP, superficiefQ, superficiefR, valX, valY);

                    reporte.append(String.format("Evaluando campo en el punto P(%.2f, %.2f):\n", valX, valY));
                    reporte.append("----------------------------------------------------\n");
                    reporte.append(String.format("DIVERGENCIA ESCALAR RESULTANTE: ∇ · F = %.4f\n", divergencia));
                    reporte.append("====================================================\n");
                    break;

                case "Calcular Rotacional ∇ × F":
                    reporte.append("-> MÓDULO DE CAMPOS VECTORIALES: ROTACIONAL (∇ × F)\n");
                    reporte.append("Fórmula: rot F = (∂R/∂y)i - (∂R/∂x)j + (∂Q/∂x - ∂P/∂y)k\n");
                    reporte.append("----------------------------------------------------\n");

                    String[] componentesRot = ecuacionLimpia.split(",");

                    // Se corrigen las funciones lambda agregando el detector correspondiente
                    BiFunction<Double, Double, Double> superficiefRotP = (x, y) ->
                            EvaluadorExpresion.evaluarPostfijo(tokensPostfijos, x, y, 0.0, valorConstanteGenerica, detector);
                    BiFunction<Double, Double, Double> superficiefRotQ = (x, y) ->
                            componentesRot.length > 1 ? superficiefRotP.apply(x, y) : 0.0;
                    BiFunction<Double, Double, Double> superficiefRotR = (x, y) -> 0.0;

                    double[] rotacional = FormularioCalculoVectorial.calcularRotacional(superficiefRotP, superficiefRotQ, superficiefRotR, valX, valY);

                    reporte.append(String.format("Evaluando campo en el punto P(%.2f, %.2f):\n", valX, valY));
                    reporte.append("----------------------------------------------------\n");
                    reporte.append(String.format("VECTOR ROTACIONAL RESULTANTE: ∇ × F =\n%.4fi + %.4fj + %.4fk\n", rotacional[0], rotacional[1], rotacional[2]));
                    reporte.append("====================================================\n");
                    break;

                default:
                    reporte.append("Operación reconocida sin rutina asignada.");
                    break;
            }

            txtResultados.setText(reporte.toString());

        } catch (Exception e) {
            txtResultados.setText("====================================================\n" +
                    "          ERROR AL PROCESAR LA EXPRESIÓN            \n" +
                    "====================================================\n" +
                    "Detalle del error: " + e.getMessage());
        }
    }
}