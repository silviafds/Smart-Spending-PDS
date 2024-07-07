import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

interface Transacao {
    dado: string;
    valor: number;
    imposto: number;
    lucro: number;
}

interface ChartProps {
    data: Transacao[];
}

const GraficoColunaVertical: React.FC<ChartProps> = ({ data }) => {
    const chartRef = useRef<HTMLDivElement>(null);

    const chartData: { name: string; value: number; imposto: number; lucro: number; valueColor: string; taxColor: string }[] = [];

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

                while (taxColor === valueColor) {
                    taxColor = getRandomPastelColor();
                }

                chartData.push({
                    name: data[prop].dado,
                    value: data[prop].valor,
                    imposto: data[prop].imposto,
                    lucro: data[prop].lucro,
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
                    formatter: (params: any) => {
                        let result = '';
                        params.forEach((param: any) => {
                            if (param.value !== null && param.value !== undefined) {
                                result += `${param.seriesName}: ${param.value}<br>`;
                            }
                        });
                        return result;
                    }
                },
                series: [
                    {
                        name: 'Valor',
                        data: chartData.map(item => ({
                            value: item.value,
                            itemStyle: { color: item.valueColor }
                        })),
                        type: 'bar' as const, // Use type assertion
                        stack: 'total'
                    },
                    ...(chartData.some(item => item.imposto !== null && item.imposto !== undefined) ? [{
                        name: 'Imposto',
                        data: chartData.filter(item => item.imposto !== null && item.imposto !== undefined).map(item => ({
                            value: item.imposto,
                            itemStyle: { color: item.taxColor }
                        })),
                        type: 'bar' as const, // Use type assertion
                        stack: 'total'
                    }] : []),
                    ...(chartData.some(item => item.lucro !== null && item.lucro !== undefined) ? [{
                        name: 'Lucro',
                        data: chartData.filter(item => item.lucro !== null && item.lucro !== undefined).map(item => ({
                            value: item.lucro,
                            itemStyle: { color: item.taxColor }
                        })),
                        type: 'bar' as const, // Use type assertion
                        stack: 'total'
                    }] : [])
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
