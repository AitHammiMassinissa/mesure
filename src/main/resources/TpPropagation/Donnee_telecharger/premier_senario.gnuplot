set terminal png
set encoding utf8
set title "Evolution premier senario"
set xlabel 'Jours'
set ylabel "infections"
set yrange[0:100]
set output './src/main/resources/TpPropagation/Donnee_telecharger/Evolution_premier_senario.png'
plot "./src/main/resources/TpPropagation/Donnee_telecharger/Evoloution_premier_senario.dat" notitle with linesp lt 1 pt 1 lc 1
