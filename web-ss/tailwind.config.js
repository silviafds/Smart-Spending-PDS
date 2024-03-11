/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{tsx,jsx,ts,html,js}"],
  theme: {
    extend: {
      colors: {
        principal: '#00264D',
        secundaria_esmeralda: '#029d02',
        secundaria_cinza: '#D3D3D3',
        destaque: '#FFD700',
        destaque_laranja: '#FF8C00',
        erros: '#D60000',
        bota_acao_hover: '#24bd24',
      },
    },
  },
  plugins: [],
}

