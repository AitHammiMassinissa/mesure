set terminal png
set encoding utf8
set title "Evolution premier senario"
set xlabel 'Jours'
set ylabel "Infections"
set yrange[0:100]
set output './src/main/resources/TpPropagation/Donnee_Aleat/Evolution_premier_senario.png'
plot "./src/main/resources/TpPropagation/Donnee_Aleat/Evoloution_premier_senario.dat" notitle with linesp lt 1 pt 1 lc 1
