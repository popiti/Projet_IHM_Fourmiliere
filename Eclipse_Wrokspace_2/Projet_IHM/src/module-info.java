module Projet_IHM {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens jeudesFourmis.vue to javafx.graphics, javafx.fxml;
}
