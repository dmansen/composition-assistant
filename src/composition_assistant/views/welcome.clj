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

(defpage [:get "/comp"] {:keys [scale-name starting-note]}
  (common/layout
   [:form {:action "/comp"}
    [:select {:name "scale-name"}
     (map (fn [name] [:option {:value name} name])
          (keys all-scales))]
    [:select {:name "starting-note"}
     (map (fn [tone] [:option {:value (str tone)} (str tone)])
          all-starting-tones)]
    [:input {:type "submit" :value "Go"}]]
   [:p (str (all-scales scale-name))]
   (if starting-note
     (let [sevenths (vec (roots-and-chords (all-scales scale-name) (symbol starting-note)))]
       (map (fn [scale]
              (let [root (scale 0)
                    svnth (scale 1)
                    svn-notes (scale 2)]
                [:div {:class "scale-tone"} "Scale tone:"
                 [:div (str "Root: " root)]
                 [:div (str "Seventh: " svnth)]
                 [:div (str "Tones: " (vec svn-notes))]]))
            sevenths)))))