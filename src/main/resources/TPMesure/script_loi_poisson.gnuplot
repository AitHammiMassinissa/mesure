set term png
set title "Degree distribution"
set xlabel 'k'
set ylabel 'p(k)'
set output "./src/main/resources/TPMesure/loi_poisson_graphe.png"
set logscale xy
set yrange [1e-6:1]
#gama 
if (x=0) {poisson(x) = 6.62208890914917**x * exp(-6.62208890914917) } else {poisson(x) = 6.62208890914917**x * exp(-6.62208890914917) /(gamma(x)*x)}
plot poisson(x) title 'distribution de Poisson'

