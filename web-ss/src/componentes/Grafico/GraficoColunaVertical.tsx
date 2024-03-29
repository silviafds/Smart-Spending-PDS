import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

interface ChartProps {
    data: { name: string; value: number }[];
}

const GraficoColunaVertical: React.FC<ChartProps> = ({ data }) =>  {
    const chartRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (chartRef.current && data.length > 0) {
            const myChart = echarts.init(chartRef.current);
            const option: echarts.EChartsOption = {
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.name),
                    axisLine: {
                        lineStyle: {
                            color: '#42424b' // Cor do eixo x
                        }
                    },
                    axisLabel: {
                        color: '#42424b' // Cor dos rótulos do eixo x
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#42424b' // Cor do eixo y
                        }
                    },
                    axisLabel: {
                        color: '#42424b' // Cor dos rótulos do eixo y
                    }
                },
                tooltip: {
                    trigger: 'axis', // Exibir tooltip quando passar o mouse sobre o eixo
                    axisPointer: {
                        type: 'shadow' // Exibir tooltip na sombra da barra
                    }
                },
                series: [
                    {
                        data: data.map(item => item.value),
                        type: 'bar',
                        itemStyle: {
                            color: '#3f74d2' // Cor das barras
                        }
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
