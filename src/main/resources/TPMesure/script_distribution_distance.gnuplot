set terminal png
set title "Distance distribution"
set xlabel 'distance'
set ylabel 'p(d)'
set output "./src/main/resources/TPMesure/Dis_distibution.png"
plot './src/main/resources/TPMesure/DistributionDistance.dat' title "DistrubtionDistance"
