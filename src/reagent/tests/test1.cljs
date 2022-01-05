(ns reagent.tests.test1
  (:require [reagent.dom :as rdom]))

(defn simple-component [name]
  [:div#app
   [:p (str "I am a component! " name)]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn render-simple [name]
  (rdom/render
   [simple-component name]
   ;; (.-body js/document) ;; alternative to next
   (js/document.getElementById "app")))

(comment
(render-simple "Mark")
)
