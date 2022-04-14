set terminal png
set encoding utf8
set title "Evolution troisieme senario"
set xlabel 'Jours'
set ylabel "Infections"
set yrange[0:100]
set output './src/main/resources/TpPropagation/Donnee_Barabasi/Evolution_troisieme_senario.png'
plot "./src/main/resources/TpPropagation/Donnee_Barabasi/Evolution_troisieme_senario.dat" notitle with linesp lt 1 pt 1 lc 1
