(ns generative-art-quil.euclidean
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def width 100)

(def height 60)

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  (let [m width n height]
    {:color 100
     :angle 0

     ;; const
     :x-pos 0
     :y-pos 0

     ;; state
     :itr   0
     :wd    n
     :m     m
     :n     n}))

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  {:color (:color state)
   :angle (+ (:angle state) 0.1)

   :itr   (inc (:itr state))
   :m (:m state)
   :n (:n state)
   :wd (:wd state)
   :x-pos (:x-pos state)
   :y-pos (:y-pos state)})

(defn draw-rec [x y w num]
  (when (<= (+ x w) num)
    (q/rect x y w w)
    (recur (+ x w) y w num)))

(defn gcd [m n]
  (if (zero? n)
    m
    (recur n (mod m n))))

(defn draw-state [{:keys [m n wd x-pos y-pos itr] :as state}]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/fill (:color state) 255 255)

  (let [t (int (/ m n))]
    (dotimes [_ t]
      (draw-rec x-pos y-pos wd m))))


(q/defsketch generative-art-quil
             :title "Visualize Euclidean Algorithm"
             :size [width height]
             ; setup function called only once, during sketch initialization.
             :setup setup
             ; update-state is called on each iteration before draw-state.
             :update update-state
             :draw draw-state
             :features [:keep-on-top]
             ; This sketch uses functional-mode middleware.
             ; Check quil wiki for more info about middlewares and particularly
             ; fun-mode.
             :middleware [m/fun-mode])
