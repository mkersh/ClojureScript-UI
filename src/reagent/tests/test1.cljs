;;; First really simple reagent tests
(ns reagent.tests.test1
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]))

(defn very-simple []
  [:div "Hello World!!"])

(defn simple-component [name]
  [:div
   [:p (str "I am a component 3333! " name)]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])])

(defn lister-user []
  [:div
   "Here is a list:"
   [lister (range 3)]])

(defn render [component]
  (rdom/render
   [component] 
   ;; (.-body js/document) ;; alternative to next
   (js/document.getElementById "app")))

(comment

(render very-simple)
(render (fn [][simple-component "Mark 3334567"]))
(render lister-user)

)

(def click-count (r/atom 0))

(defn counting-component [msg]
  [:div
   (str msg " The atom " [:code "click-count"] " has value: ")
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

(defn render-counting []
  (rdom/render
   [counting-component] ;; need to include in a vector to be a react component and for the @click-count to be refreshed
   ;; (.-body js/document) ;; alternative to next
   (js/document.getElementById "app")))
   
(comment
  @click-count
  (render-counting) ;; explicit render function for counting-component
  (render render-counting) ;; generic render function
  (render (fn [] [counting-component "Hello World"])) ;; If render-counting took parameters you would need to call like this

 ;; 
  )