import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

interface Transacao {
    dado: string;
    valor: number;
}

interface ChartProps {
    data: Transacao[];
}

const GraficoDePizza: React.FC<ChartProps> = ({ data }) =>  {
    const chartRef = useRef<HTMLDivElement>(null);

    const chartData: { name: string; value: number }[] = [];

    for(let prop in data) {
        chartData.push({name: data[prop].dado, value: data[prop].valor})
    }

    useEffect(() => {
        if (chartRef.current && chartData.length > 0) {
            const myChart = echarts.init(chartRef.current);
            const option: echarts.EChartsOption = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 10,
                    data: chartData.map(item => item.name)
                },
                series: [
                    {
                        name: 'Quantidade',
                        type: 'pie',
                        radius: '80%',
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: chartData.map(item => ({ name: item.name, value: item.value }))
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

export default GraficoDePizza;
