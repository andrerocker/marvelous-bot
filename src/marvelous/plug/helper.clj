(ns marvelous.plug.helper
  (:gen-class))

(defn extract-chan-message [line]
  (let [[_ sender _ channel message] (re-matches #":(.*)!(.*)PRIVMSG #(.*) :(.*)" line)]
      {:sender sender :channel channel :message message}))

(defn send-chan-user-message [props message]
  (str "PRIVMSG #" (:channel props) " :" (:sender props) ": " message))
