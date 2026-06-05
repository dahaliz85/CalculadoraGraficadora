package com.biomath3d.entity;

/**
 * Entidad que simula la tabla 'dominios_graficacion'.
 * Muestra la normalización relacional mediante una relación 1:1 con SimulacionEntity.
 */
public class DominioGraficacionEntity {

    // @Id - Primary Key
    private Integer idDominio;

    // @ForeignKey(references = SimulacionEntity.class)
    private Integer simulacionId;

    private double limiteXMin;
    private double limiteXMax;
    private double limiteYMin;
    private double limiteYMax;
    private double constanteParametroA;

    public DominioGraficacionEntity() {}

    public DominioGraficacionEntity(Integer idDominio, Integer simulacionId, double limiteXMin, double limiteXMax, double limiteYMin, double limiteYMax, double constanteParametroA) {
        this.idDominio = idDominio;
        this.simulacionId = simulacionId;
        this.limiteXMin = limiteXMin;
        this.limiteXMax = limiteXMax;
        this.limiteYMin = limiteYMin;
        this.limiteYMax = limiteYMax;
        this.constanteParametroA = constanteParametroA;
    }

    // Getters y Setters
    public Integer getIdDominio() { return idDominio; }
    public void setIdDominio(Integer idDominio) { this.idDominio = idDominio; }

    public Integer getSimulacionId() { return simulacionId; }
    public void setSimulacionId(Integer simulacionId) { this.simulacionId = simulacionId; }

    public double getLimiteXMin() { return limiteXMin; }
    public void setLimiteXMin(double limiteXMin) { this.limiteXMin = limiteXMin; }

    public double getLimiteXMax() { return limiteXMax; }
    public void setLimiteXMax(double limiteXMax) { this.limiteXMax = limiteXMax; }

    public double getLimiteYMin() { return limiteYMin; }
    public void setLimiteYMin(double limiteYMin) { this.limiteYMin = limiteYMin; }

    public double getLimiteYMax() { return limiteYMax; }
    public void setLimiteYMax(double limiteYMax) { this.limiteYMax = limiteYMax; }

    public double getConstanteParametroA() { return constanteParametroA; }
    public void setConstanteParametroA(double constanteParametroA) { this.constanteParametroA = constanteParametroA; }
}