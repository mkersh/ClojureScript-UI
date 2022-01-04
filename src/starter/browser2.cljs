(ns starter.browser2)

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start"))

(defn init [] 
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

(defn hello2 [] "Hello from (hello2) function")

;; uncomment this to alter the provided "app" DOM element
(set! (.-innerHTML (js/document.getElementById "app")) (hello2))
