# le-monde-des-blocs
Le monde des blocs BlocksWorld est un exemple couramment utilisé en intelligence artificielle, en particulier pour illustrer ou tester des algorithmes de planification.
Dans unmonde des blocs, on a n blocs et m piles. Dans ce monde chaque bloc peut être déplacé
selon des règles spécifiques. L’objectif principal de ce projet est de concevoir un système
permettant de gérer ces configurations, en définissant les variables et les contraintes asso-
ciées à chaque blocs, ainsi qu’en implémentant des actions de planification pour manipuler
ces blocs.




---------Mode d'emploi ------

- Ouvrir un terminal
- Créer un dossier build/ à la racine
- Se placer dans le dossier src/
- puis taper les commandes suivantes : 

#----pour la compilation -----

    javac -d ../build -cp "../lib/*" modelling/*.java planning/*.java cp/*.java datamining/*.java blocksworld/*.java

#---- pour éxécuter les classes éxécutables ------

    -- pour les demo  : 
    	java -cp "../build:../lib/*" blocksworld.DemoConstraint
    
    	java -cp "../build:../lib/*" blocksworld.DemoPlannificateur
    

    	java -cp "../build:../lib/*" blocksworld.DemoSolverRegular
    

    	java -cp "../build:../lib/*" blocksworld.DemoSolverIncrease
    

    	java -cp "../build:../lib/*" blocksworld.DemoDatabase

