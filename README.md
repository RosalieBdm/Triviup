# Triviup

## Prompt ChatGPT
    Fragment d'accueil : Ce fragment affiche une liste de catégories de quiz, comme les animaux, les sciences, la géographie, etc. L'utilisateur peut sélectionner une catégorie pour commencer un quiz.

    Fragment de quiz : Ce fragment affiche les questions de quiz une par une, en fonction de la catégorie sélectionnée. Utilisez l'API Open Trivia Database pour obtenir des questions de quiz. API : https://opentdb.com/api_config.php

    Fragment de réponse : Ce fragment affiche la réponse correcte après que l'utilisateur ait soumis sa réponse et indique si l'utilisateur a répondu correctement ou non.

    Fragment de résultats : Ce fragment affiche le score final de l'utilisateur après avoir terminé le quiz, ainsi que les réponses correctes et incorrectes.

    Fragment de classement : Ce fragment affiche un classement des meilleurs scores des utilisateurs pour chaque catégorie de quiz. Utilisez une base de données Firebase pour stocker et récupérer les scores des utilisateurs. API : https://firebase.google.com/docs/database

    Fragment d'exploration : Ce fragment permet à l'utilisateur de parcourir des faits intéressants et insolites dans différentes catégories. Utilisez l'API Numbers pour obtenir des faits basés sur des chiffres. API : http://numbersapi.com

    Fragment des paramètres : Ce fragment permet à l'utilisateur de modifier les paramètres de l'application, tels que la difficulté du quiz, la durée du temps imparti pour répondre à chaque question et la possibilité d'activer ou de désactiver certaines catégories.
    
    
## Commit v1
   - fragment category:
        - liste de categories , mène au fragment questions en cliquent sur la categorie
   - Fragment Questions:
        - Stocke 10 questions dans une room database 
        - affiche la question et les réponses 
        - en cliquant sur une réponse, la bonne réponse devient verte et les autres rouges et un bouton "prochaine question apparait", à la 10eme question un bouton "results apparait" qui mène au fragment Results
        - le score et le numero de la question sont affichés en haut 
        - bouton Category qui permet de retourner au fragment category ( sert pour les tests, à enlever à la fin)
   - Fragment Results:
        - affiche le score final du joueur
        - bouton Category pour retourner à la liste des catégories
   # A modifier
    - modifier la requete API pour récuperer tous les types de questions et pas seulement les "multiple"
    - ajouter le choix de la difficulté 
    - ajouter un temps imparti pour répondre et pondérer le score
    - faire un joli thème
    - mettre le score final dans la firebase
    - ajouter le fragment menu qui mène aux catégories 
    - vérifier qu'il y ait pas deux fois la même question 
    
