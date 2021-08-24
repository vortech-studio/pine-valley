const express = require("express");
const User = require("../models/user");
const auth = require("../middleware/auth");
const router = new express.Router();

router.get("/", function (req, res) {
  res.json({ message: "Rest API for retrofit 2.0 application" });
});

router.post("/users", async (req, res) => {
  const user = new User(req.body);

  try {
    await user.save();
    // const token = await user.generateAuthToken();
    res.json({ error: false, message: "Registration Successful", user });
  } catch (e) {
    res.json({ error: true, message: e.message, user: null });
  }
});

router.post("/users/login", async (req, res) => {
  try {
    const user = await User.findByCredentials(
      req.body.email,
      req.body.password
    );
    const token = await user.generateAuthToken();
    res.json({ error: false, message: "Login Successful", user });
  } catch (e) {
    res.json({ error: true, message: e.message, user: null });
  }
});

router.post("/users/logout", auth, async (req, res) => {
  try {
    req.user.tokens = req.user.tokens.filter((token) => {
      return token.token !== req.token;
    });
    await req.user.save();

    res.json.send();
  } catch (e) {
    res.json.send(e);
  }
});

router.post("/users/logoutAll", auth, async (req, res) => {
  try {
    req.user.tokens = [];
    await req.user.save();
    res.json.send();
  } catch (e) {
    res.json.send(e);
  }
});

router.get("/users/me", auth, async (req, res) => {
  res.status(200).send(req.user);
});

module.exports = router;
