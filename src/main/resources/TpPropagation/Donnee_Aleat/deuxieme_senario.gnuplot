set terminal png
set encoding utf8
set title "Evolution deuxieme senario"
set xlabel 'Jours'
set ylabel "Infections"
set yrange[0:100]
set output './src/main/resources/TpPropagation/Donnee_Aleat/Evolution_deuxieme_senario.png'
plot "./src/main/resources/TpPropagation/Donnee_Aleat/Evolution_deuxieme_senario.dat" notitle with linesp lt 1 pt 1 lc 1

