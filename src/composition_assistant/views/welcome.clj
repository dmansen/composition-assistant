(ns composition-assistant.views.welcome
  (:require [composition-assistant.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        composition-assistant.scales
        composition-assistant.absolute
        composition-assistant.core))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to comp-assist"]))

(defn tone-component
  [scale-name starting-note]
  [:div
   [:div [:h3 "Scale tones: " (str (vec (notes-to-pitches (all-scales scale-name) (symbol starting-note))))]]
   (let [tones (vec (roots-and-chords (all-scales scale-name) (symbol starting-note)))]
     (map (fn [scale]
            [:div
             [:h3 (str "Scale tone: " (scale 0))]
             [:div {:class "scale-tone"}
              [:div {:class "root"} (str "Root: " (scale 0))]
              [:div {:class "triad"} (str "Triad: " (scale 1))]
              [:div {:class "seventh"} (str "Seventh: " (scale 2))]
              [:div {:class "seventh-tones"} (str "Tones: " (vec (scale 3)))]]])
          tones))])

(defpage [:get "/comp"] {:keys [scale-name starting-note]}
  (common/layout
   [:form {:action "/comp"}
    [:div "Mode: " [:select {:name "scale-name"}
       (map (fn [name] [:option {:value name} name])
            (keys all-scales))]]
    [:div "Start on: " [:select {:name "starting-note"}
       (map (fn [tone] [:option {:value (str tone)} (str tone)])
            all-starting-tones)]]
    [:input {:type "submit" :value "Go"}]]
   (if scale-name
     [:h3 (str "Intervals: "(all-scales scale-name))])
   (if (and scale-name starting-note)
     (tone-component scale-name starting-note))))