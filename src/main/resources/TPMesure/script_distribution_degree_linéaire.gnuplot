set terminal png
set title "Degree distribution lineaire"
set xlabel 'k'
set ylabel 'p(k)'
set output "./src/main/resources/TPMesure/Deg_dist_lin.png"
plot './src/main/resources/TPMesure/DistributionDegree.dat' title "DistrubtionDegreelineaire"




