(ns reagent.tests.test1
  (:require [reagent.dom :as rdom]))

(defn very-simple []
  [:div "Hello World!!"])

(defn simple-component [name]
  [:div
   [:p (str "I am a component! " name)]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn render [component]
  (rdom/render
   component
   ;; (.-body js/document) ;; alternative to next
   (js/document.getElementById "app")))

(comment
(render very-simple)
(render (simple-component "Mark 222"))
)
