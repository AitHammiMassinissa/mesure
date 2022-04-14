set terminal png
set title "Degree distribution loglog"
set xlabel 'k'
set ylabel 'p(k)'
set output "./src/main/resources/TPMesure/Deg_dist_loglog.png"
set logscale xy
plot './src/main/resources/TPMesure/DistributionDegree.dat' title "DistributionDegreeloglog"

