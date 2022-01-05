(ns starter.browser
;; Requiring namespace(s) from here will autoload them
(:require [reagent.tests.test1 :as rt]
[reagent.dom :as rdom]
[reagent.core :as r]
)
)

(enable-console-print!)

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start"))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))


(js/console.log "Hello World!!!")

(println "can you see this as well!!!")


(defn hello [] "Hello from shadow-cljs. AAA BBB")

;; uncomment this to alter the provided "app" DOM element
;;(set! (.-innerHTML (js/document.getElementById "app")) (hello))

(defn render [component]
  (rdom/render
   component
   ;; (.-body js/document) ;; alternative to next
   (js/document.getElementById "app")))

(def click-count (r/atom 0))

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])


(render counting-component)

