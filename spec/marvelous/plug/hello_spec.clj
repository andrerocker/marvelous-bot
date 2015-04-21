(ns marvelous.plug.hello-spec
  (:require [speclj.core :refer :all]
            [marvelous.plug.hello :refer :all]))

(describe "#hello-plugin"
  (it "match any 'hello' message on a IRC message proto"
      (let [matcher (:matcher hello-user)]
        (should-not-be-nil (matcher "hello"))
        (should-not-be-nil (matcher "hellows"))
        (should-not-be-nil (matcher "dashellows"))
        (should-be-nil (matcher "blablabla"))
        (should-be-nil (matcher "andre power power acme acme"))))

  (it "send a message to the user"
      (let [response ((:handler-fn hello-user) ":andrerocker!~andrerock@179.110.138.127 PRIVMSG #marvelous-development :hello")]
        (should-contain "PRIVMSG #marvelous-development :andrerocker:" response))))

(run-specs)
