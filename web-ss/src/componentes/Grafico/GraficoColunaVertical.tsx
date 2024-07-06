import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

interface Transacao {
    dado: string;
    valor: number;
    imposto: number;
}

interface ChartProps {
    data: Transacao[];
}

const GraficoColunaVertical: React.FC<ChartProps> = ({ data }) =>  {
    const chartRef = useRef<HTMLDivElement>(null);

    const chartData: { name: string; value: number; imposto: number; valueColor: string; taxColor: string }[] = [];

    // Função para gerar uma cor pastel aleatória
    const getRandomPastelColor = () => {
        const hue = Math.floor(Math.random() * 360);
        return `hsl(${hue}, 100%, 80%)`; // Cores pastel têm alta luminosidade e saturação
    };

    if (data) {
        for (let prop in data) {
            if (data[prop] && data[prop].dado) {
                const valueColor = getRandomPastelColor();
                let taxColor = getRandomPastelColor();

                // Garante que a cor do imposto seja diferente da cor do valor
                while (taxColor === valueColor) {
                    taxColor = getRandomPastelColor();
                }

                chartData.push({
                    name: data[prop].dado,
                    value: data[prop].valor,
                    imposto: data[prop].imposto,
                    valueColor: valueColor,
                    taxColor: taxColor
                });
            }
        }
    }

    useEffect(() => {
        if (chartRef.current && chartData.length > 0) {
            const myChart = echarts.init(chartRef.current);
            const option: echarts.EChartsOption = {
                xAxis: {
                    type: 'category',
                    data: chartData.map(item => item.name),
                    axisLine: {
                        lineStyle: {
                            color: '#42424b'
                        }
                    },
                    axisLabel: {
                        color: '#42424b'
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#42424b'
                        }
                    },
                    axisLabel: {
                        color: '#42424b'
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: (params) => {
                        // @ts-ignore
                        const mainSeries = params[0];
                        // @ts-ignore
                        const taxSeries = params[1];
                        return `${mainSeries.seriesName}: ${mainSeries.value}<br>${taxSeries.seriesName}: ${taxSeries.value}`;
                    }
                },
                series: [
                    {
                        name: 'Valor',
                        data: chartData.map(item => ({
                            value: item.value,
                            itemStyle: { color: item.valueColor }
                        })),
                        type: 'bar',
                        stack: 'total'
                    },
                    {
                        name: 'Imposto',
                        data: chartData.map(item => ({
                            value: item.imposto,
                            itemStyle: { color: item.taxColor }
                        })),
                        type: 'bar',
                        stack: 'total'
                    }
                ]
            };

            myChart.setOption(option);

            return () => {
                myChart.dispose();
            };
        }
    }, [data]);

    return <div ref={chartRef} className="w-full h-96 border-solid border-slate-300 border rounded-md" />;
}

export default GraficoColunaVertical;
