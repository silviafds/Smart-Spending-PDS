import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

interface Transacao {
    dado: string;
    valor: number;
}

interface ChartProps {
    data: Transacao[];
}

const GraficoColunaVertical: React.FC<ChartProps> = ({ data }) =>  {
    const chartRef = useRef<HTMLDivElement>(null);

    const chartData: { name: string; value: number }[] = [];

    for(let prop in data) {
        chartData.push({name: data[prop].dado, value: data[prop].valor})
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
                    }
                },
                series: [
                    {
                        data: chartData.map(item => item.value),
                        type: 'bar',
                        itemStyle: {
                            color: '#3f74d2'
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