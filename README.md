# Triviup

## Prompt ChatGPT
    Fragment d'accueil : Ce fragment affiche une liste de catégories de quiz, comme les animaux, les sciences, la géographie, etc. L'utilisateur peut sélectionner une catégorie pour commencer un quiz.

    Fragment de quiz : Ce fragment affiche les questions de quiz une par une, en fonction de la catégorie sélectionnée. Utilisez l'API Open Trivia Database pour obtenir des questions de quiz. API : https://opentdb.com/api_config.php

    Fragment de réponse : Ce fragment affiche la réponse correcte après que l'utilisateur ait soumis sa réponse et indique si l'utilisateur a répondu correctement ou non.

    Fragment de résultats : Ce fragment affiche le score final de l'utilisateur après avoir terminé le quiz, ainsi que les réponses correctes et incorrectes.

    Fragment de classement : Ce fragment affiche un classement des meilleurs scores des utilisateurs pour chaque catégorie de quiz. Utilisez une base de données Firebase pour stocker et récupérer les scores des utilisateurs. API : https://firebase.google.com/docs/database

    Fragment d'exploration : Ce fragment permet à l'utilisateur de parcourir des faits intéressants et insolites dans différentes catégories. Utilisez l'API Numbers pour obtenir des faits basés sur des chiffres. API : http://numbersapi.com

    Fragment des paramètres : Ce fragment permet à l'utilisateur de modifier les paramètres de l'application, tels que la difficulté du quiz, la durée du temps imparti pour répondre à chaque question et la possibilité d'activer ou de désactiver certaines catégories.