;;; First really simple reagent tests
;;; Using (mainly) examples from https://reagent-project.github.io/ 
;;; http://localhost:8020
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
  (render-counting) ;; explicit render function for counting-component
  (render counting-component) ;; generic render function
  (render (fn [] [counting-component "Hello World"])) ;; If render-counting took parameters you would need to call like this

 ;; 
  )


(defn calc-bmi [{:keys [height weight bmi] :as data}]
    (let [h (/ height 100)]
      (if (nil? bmi)
        (assoc data :bmi (/ weight (* h h)))
        (assoc data :weight (* bmi h h)))))

(def bmi-data (r/atom (calc-bmi {:height 180 :weight 80})))

(defn slider [param value min max invalidates]
  [:input {:type "range" :value value :min min :max max
           :style {:width "100%"}
           :on-change (fn [e]
                        (let [new-value (js/parseInt (.. e -target -value))]
                          (swap! bmi-data
                                 (fn [data]
                                   (-> data
                                       (assoc param new-value)
                                       (dissoc invalidates)
                                       calc-bmi)))))}])

(defn bmi-component []
  (let [{:keys [weight height bmi]} @bmi-data
        [color diagnose] (cond
                           (< bmi 18.5) ["orange" "underweight"]
                           (< bmi 25) ["inherit" "normal"]
                           (< bmi 30) ["orange" "overweight"]
                           :else ["red" "obese"])]
    [:div
     [:h3 "BMI calculator"]
     [:div
      "Height: " (int height) "cm"
      [slider :height height 100 220 :bmi]]
     [:div
      "Weight: " (int weight) "kg"
      [slider :weight weight 30 150 :bmi]]
     [:div
      "BMI: " (int bmi) " "
      [:span {:style {:color color}} diagnose]
      [slider :bmi bmi 10 50 :weight]]]))

(comment
  
  (render bmi-component) 
  
 ;; 
  )
