module ELMEJJADWALID {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens jeudesFourmis.vue to javafx.graphics, javafx.fxml;
}
