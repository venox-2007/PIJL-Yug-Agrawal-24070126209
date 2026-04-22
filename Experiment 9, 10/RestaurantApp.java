import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.beans.property.*;
import javafx.animation.*;
import javafx.util.Duration;

import java.sql.*;

public class RestaurantApp extends Application {

    static final String URL  = "jdbc:mysql://localhost:3306/restaurant_db";
    static final String USER = "root";
    static final String PASS = "Yuna1427";

    // ─── Colour Palette ───────────────────────────────────
    static final String BG_DARK    = "#0D0D0D";
    static final String BG_CARD    = "#1A1A2E";
    static final String BG_SIDEBAR = "#16213E";
    static final String ACCENT1    = "#E94560";   // red-pink
    static final String ACCENT2    = "#0F3460";   // deep blue
    static final String ACCENT3    = "#533483";   // purple
    static final String TEXT_MAIN  = "#EAEAEA";
    static final String TEXT_DIM   = "#888888";
    static final String SUCCESS    = "#00C897";
    static final String WARNING    = "#FFB830";

    // ─── Active view tracker ──────────────────────────────
    StackPane contentArea = new StackPane();
    Label activeNavLabel  = null;

    // ═════════════════════════════════════════════════════
    //  DATA MODELS
    // ═════════════════════════════════════════════════════
    public static class Restaurant {
        private final IntegerProperty id;
        private final StringProperty  name, address;
        public Restaurant(int id, String name, String address) {
            this.id      = new SimpleIntegerProperty(id);
            this.name    = new SimpleStringProperty(name);
            this.address = new SimpleStringProperty(address);
        }
        public int    getId()      { return id.get(); }
        public String getName()    { return name.get(); }
        public String getAddress() { return address.get(); }
        public IntegerProperty idProperty()      { return id; }
        public StringProperty  nameProperty()    { return name; }
        public StringProperty  addressProperty() { return address; }
    }

    public static class MenuItem {
        private final IntegerProperty id, resId;
        private final StringProperty  name;
        private final DoubleProperty  price;
        public MenuItem(int id, String name, double price, int resId) {
            this.id    = new SimpleIntegerProperty(id);
            this.name  = new SimpleStringProperty(name);
            this.price = new SimpleDoubleProperty(price);
            this.resId = new SimpleIntegerProperty(resId);
        }
        public int    getId()    { return id.get(); }
        public String getName()  { return name.get(); }
        public double getPrice() { return price.get(); }
        public int    getResId() { return resId.get(); }
        public IntegerProperty idProperty()    { return id; }
        public StringProperty  nameProperty()  { return name; }
        public DoubleProperty  priceProperty() { return price; }
        public IntegerProperty resIdProperty() { return resId; }
    }

    // ═════════════════════════════════════════════════════
    //  DB HELPER
    // ═════════════════════════════════════════════════════
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ═════════════════════════════════════════════════════
    //  START
    // ═════════════════════════════════════════════════════
    @Override
    public void start(Stage stage) {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG_DARK + ";");

        // ── Header ──
        root.setTop(buildHeader());

        // ── Sidebar ──
        root.setLeft(buildSidebar());

        // ── Content ──
        contentArea.setStyle("-fx-background-color: " + BG_DARK + ";");
        contentArea.setPadding(new Insets(20));
        root.setCenter(contentArea);

        // Default view
        showView(buildRestaurantView());

        Scene scene = new Scene(root, 1200, 720);
        stage.setTitle("RestaurantOS — Admin Panel");
        stage.setScene(scene);
        stage.show();
    }

    // ═════════════════════════════════════════════════════
    //  HEADER
    // ═════════════════════════════════════════════════════
    HBox buildHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 30, 0, 0));
        header.setPrefHeight(64);
        header.setStyle(
            "-fx-background-color: linear-gradient(to right, " + BG_SIDEBAR + ", " + ACCENT2 + ");" +
            "-fx-border-color: " + ACCENT1 + ";" +
            "-fx-border-width: 0 0 2 0;"
        );

        // Logo block
        HBox logoBlock = new HBox(10);
        logoBlock.setAlignment(Pos.CENTER);
        logoBlock.setPrefWidth(220);
        logoBlock.setPadding(new Insets(0, 20, 0, 20));

        Rectangle logoIcon = new Rectangle(36, 36);
        logoIcon.setArcWidth(8); logoIcon.setArcHeight(8);
        logoIcon.setFill(Color.web(ACCENT1));

        Label logoText = new Label("🍽 RestaurantOS");
        logoText.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 18));
        logoText.setTextFill(Color.web(TEXT_MAIN));

        logoBlock.getChildren().addAll(logoIcon, logoText);

        // Separator
        Separator sep = new Separator(Orientation.VERTICAL);
        sep.setStyle("-fx-background-color: " + ACCENT1 + ";");

        // Title
        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 14));
        titleLabel.setTextFill(Color.web(TEXT_DIM));
        titleLabel.setPadding(new Insets(0, 0, 0, 20));

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // DB Status indicator
        Circle dbDot = new Circle(6, Color.web(SUCCESS));
        Label dbLabel = new Label("MySQL Connected");
        dbLabel.setFont(Font.font("Segoe UI", 12));
        dbLabel.setTextFill(Color.web(SUCCESS));
        HBox dbStatus = new HBox(6, dbDot, dbLabel);
        dbStatus.setAlignment(Pos.CENTER);

        header.getChildren().addAll(logoBlock, sep, titleLabel, spacer, dbStatus);
        return header;
    }

    // ═════════════════════════════════════════════════════
    //  SIDEBAR
    // ═════════════════════════════════════════════════════
    VBox buildSidebar() {
        VBox sidebar = new VBox(4);
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(20, 10, 20, 10));
        sidebar.setStyle("-fx-background-color: " + BG_SIDEBAR + ";");

        Label sectionLabel = new Label("TABLES");
        sectionLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        sectionLabel.setTextFill(Color.web(TEXT_DIM));
        sectionLabel.setPadding(new Insets(10, 8, 6, 8));

        Label navRestaurant = buildNavItem("🏢  Restaurants", true);
        Label navMenuItems  = buildNavItem("🍕  Menu Items", false);

        Label sectionLabel2 = new Label("OPERATIONS");
        sectionLabel2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        sectionLabel2.setTextFill(Color.web(TEXT_DIM));
        sectionLabel2.setPadding(new Insets(18, 8, 6, 8));

        Label navInsert = buildNavItem("➕  Insert Records", false);
        Label navQuery  = buildNavItem("🔍  Query Builder", false);
        Label navUpdate = buildNavItem("✏️  Bulk Update", false);
        Label navDelete = buildNavItem("🗑️  Delete Records", false);

        navRestaurant.setOnMouseClicked(e -> {
            setActiveNav(navRestaurant);
            showView(buildRestaurantView());
        });
        navMenuItems.setOnMouseClicked(e -> {
            setActiveNav(navMenuItems);
            showView(buildMenuItemView());
        });
        navInsert.setOnMouseClicked(e -> {
            setActiveNav(navInsert);
            showView(buildInsertView());
        });
        navQuery.setOnMouseClicked(e -> {
            setActiveNav(navQuery);
            showView(buildQueryView());
        });
        navUpdate.setOnMouseClicked(e -> {
            setActiveNav(navUpdate);
            showView(buildUpdateView());
        });
        navDelete.setOnMouseClicked(e -> {
            setActiveNav(navDelete);
            showView(buildDeleteView());
        });

        activeNavLabel = navRestaurant;

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label version = new Label("v1.0.0  •  JDBC + JavaFX");
        version.setFont(Font.font("Segoe UI", 10));
        version.setTextFill(Color.web(TEXT_DIM));
        version.setPadding(new Insets(0, 0, 0, 8));

        sidebar.getChildren().addAll(
            sectionLabel, navRestaurant, navMenuItems,
            sectionLabel2, navInsert, navQuery, navUpdate, navDelete,
            spacer, version
        );
        return sidebar;
    }

    Label buildNavItem(String text, boolean active) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        lbl.setPrefWidth(200);
        lbl.setPadding(new Insets(10, 12, 10, 12));
        lbl.setCursor(javafx.scene.Cursor.HAND);
        if (active) {
            lbl.setStyle(
                "-fx-background-color: " + ACCENT1 + "22;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + ACCENT1 + ";" +
                "-fx-border-width: 0 0 0 3;" +
                "-fx-text-fill: " + ACCENT1 + ";"
            );
        } else {
            lbl.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: " + TEXT_DIM + ";"
            );
            lbl.setOnMouseEntered(e -> {
                if (activeNavLabel != lbl)
                    lbl.setStyle("-fx-background-color: #FFFFFF11;-fx-background-radius:8;-fx-text-fill:" + TEXT_MAIN + ";");
            });
            lbl.setOnMouseExited(e -> {
                if (activeNavLabel != lbl)
                    lbl.setStyle("-fx-background-color:transparent;-fx-background-radius:8;-fx-text-fill:" + TEXT_DIM + ";");
            });
        }
        return lbl;
    }

    void setActiveNav(Label lbl) {
        if (activeNavLabel != null)
            activeNavLabel.setStyle(
                "-fx-background-color:transparent;-fx-background-radius:8;-fx-text-fill:" + TEXT_DIM + ";"
            );
        lbl.setStyle(
            "-fx-background-color:" + ACCENT1 + "22;" +
            "-fx-background-radius:8;" +
            "-fx-border-color:" + ACCENT1 + ";" +
            "-fx-border-width:0 0 0 3;" +
            "-fx-text-fill:" + ACCENT1 + ";"
        );
        activeNavLabel = lbl;
    }

    // ═════════════════════════════════════════════════════
    //  SHOW VIEW (with fade animation)
    // ═════════════════════════════════════════════════════
    void showView(Node view) {
        view.setOpacity(0);
        contentArea.getChildren().setAll(view);
        FadeTransition ft = new FadeTransition(Duration.millis(250), view);
        ft.setFromValue(0); ft.setToValue(1);
        ft.play();
    }

    // ═════════════════════════════════════════════════════
    //  SHARED UI HELPERS
    // ═════════════════════════════════════════════════════
    VBox buildCard(String title, String subtitle, Node... content) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(24));
        card.setStyle(
            "-fx-background-color:" + BG_CARD + ";" +
            "-fx-background-radius:12;" +
            "-fx-border-color:#FFFFFF18;" +
            "-fx-border-radius:12;" +
            "-fx-border-width:1;"
        );
        DropShadow ds = new DropShadow(20, Color.BLACK);
        ds.setOffsetY(4);
        card.setEffect(ds);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLbl.setTextFill(Color.web(TEXT_MAIN));

        Label subLbl = new Label(subtitle);
        subLbl.setFont(Font.font("Segoe UI", 12));
        subLbl.setTextFill(Color.web(TEXT_DIM));

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color:#FFFFFF18;");

        card.getChildren().addAll(titleLbl, subLbl, sep);
        card.getChildren().addAll(content);
        return card;
    }

    TextField styledField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(
            "-fx-background-color:#FFFFFF0D;" +
            "-fx-background-radius:6;" +
            "-fx-border-color:#FFFFFF22;" +
            "-fx-border-radius:6;" +
            "-fx-text-fill:" + TEXT_MAIN + ";" +
            "-fx-prompt-text-fill:" + TEXT_DIM + ";" +
            "-fx-padding:8 12 8 12;" +
            "-fx-font-size:13;"
        );
        tf.setPrefWidth(180);
        tf.focusedProperty().addListener((obs, o, n) -> {
            if (n) tf.setStyle(tf.getStyle().replace("#FFFFFF22", ACCENT1));
            else   tf.setStyle(tf.getStyle().replace(ACCENT1, "#FFFFFF22"));
        });
        return tf;
    }

    Button styledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        btn.setTextFill(Color.WHITE);
        btn.setPadding(new Insets(8, 18, 8, 18));
        btn.setCursor(javafx.scene.Cursor.HAND);
        btn.setStyle(
            "-fx-background-color:" + color + ";" +
            "-fx-background-radius:6;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: derive(" + color + ", 20%);" +
            "-fx-background-radius:6;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color:" + color + ";" +
            "-fx-background-radius:6;"
        ));
        DropShadow glow = new DropShadow(10, Color.web(color));
        btn.setEffect(glow);
        return btn;
    }

    Label statusLabel() {
        Label lbl = new Label(" ");
        lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        lbl.setPadding(new Insets(6, 12, 6, 12));
        lbl.setStyle("-fx-background-radius:6;");
        return lbl;
    }

    void setStatus(Label lbl, String msg, boolean success) {
        lbl.setText(msg);
        String color = success ? SUCCESS : ACCENT1;
        lbl.setTextFill(Color.web(color));
        lbl.setStyle(
            "-fx-background-color:" + color + "22;" +
            "-fx-background-radius:6;" +
            "-fx-border-color:" + color + "44;" +
            "-fx-border-radius:6;"
        );
        FadeTransition ft = new FadeTransition(Duration.millis(300), lbl);
        ft.setFromValue(0); ft.setToValue(1);
        ft.play();
    }

    <T> TableView<T> styledTable() {
        TableView<T> table = new TableView<>();
        table.setStyle(
            "-fx-background-color:#FFFFFF08;" +
            "-fx-background-radius:8;" +
            "-fx-border-color:#FFFFFF18;" +
            "-fx-border-radius:8;" +
            "-fx-table-cell-border-color:transparent;"
        );
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setFixedCellSize(40);
        return table;
    }

    // ═════════════════════════════════════════════════════
    //  VIEW 1 — RESTAURANTS
    // ═════════════════════════════════════════════════════
    Node buildRestaurantView() {
        TableView<Restaurant> table = styledTable();
        TableColumn<Restaurant, Integer> colId   = new TableColumn<>("ID");
        TableColumn<Restaurant, String>  colName = new TableColumn<>("Restaurant Name");
        TableColumn<Restaurant, String>  colAddr = new TableColumn<>("Address");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddr.setCellValueFactory(new PropertyValueFactory<>("address"));
        styleTableColumns(colId, colName, colAddr);
        table.getColumns().addAll(colId, colName, colAddr);
        VBox.setVgrow(table, Priority.ALWAYS);

        Label status = statusLabel();
        Button btnRefresh = styledButton("⟳  Refresh", ACCENT2);
        btnRefresh.setOnAction(e -> {
            loadRestaurants(table);
            setStatus(status, "✅ Table refreshed.", true);
        });

        HBox toolbar = new HBox(10, btnRefresh, status);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        loadRestaurants(table);
        return buildCard(
            "🏢  Restaurant Table",
            "All registered restaurants in the database",
            toolbar, table
        );
    }

    void loadRestaurants(TableView<Restaurant> table) {
        ObservableList<Restaurant> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Restaurant")) {
            while (rs.next())
                list.add(new Restaurant(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address")));
        } catch (SQLException e) { showAlert("DB Error", e.getMessage()); }
        table.setItems(list);
    }

    // ═════════════════════════════════════════════════════
    //  VIEW 2 — MENU ITEMS
    // ═════════════════════════════════════════════════════
    Node buildMenuItemView() {
        TableView<MenuItem> table = styledTable();
        TableColumn<MenuItem, Integer> colId    = new TableColumn<>("ID");
        TableColumn<MenuItem, String>  colName  = new TableColumn<>("Item Name");
        TableColumn<MenuItem, Double>  colPrice = new TableColumn<>("Price (₹)");
        TableColumn<MenuItem, Integer> colResId = new TableColumn<>("Restaurant ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        styleTableColumns(colId, colName, colPrice, colResId);
        table.getColumns().addAll(colId, colName, colPrice, colResId);
        VBox.setVgrow(table, Priority.ALWAYS);

        Label status = statusLabel();
        Button btnAll    = styledButton("All Items", ACCENT2);
        Button btnPrice  = styledButton("Price ≤ 100", ACCENT3);
        Button btnCafe   = styledButton("Cafe Java", "#1B5E20");

        btnAll.setOnAction(e -> {
            loadMenuItems(table, "SELECT * FROM MenuItem");
            setStatus(status, "✅ Showing all menu items.", true);
        });
        btnPrice.setOnAction(e -> {
            loadMenuItems(table, "SELECT * FROM MenuItem WHERE Price <= 100");
            setStatus(status, "✅ Showing items with Price ≤ 100.", true);
        });
        btnCafe.setOnAction(e -> {
            loadMenuItems(table,
                "SELECT m.Id,m.Name,m.Price,m.ResId FROM MenuItem m " +
                "JOIN Restaurant r ON m.ResId=r.Id WHERE r.Name='Cafe Java'");
            setStatus(status, "✅ Showing Cafe Java menu items.", true);
        });

        HBox toolbar = new HBox(10, btnAll, btnPrice, btnCafe);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        loadMenuItems(table, "SELECT * FROM MenuItem");
        return buildCard(
            "🍕  Menu Items Table",
            "Browse and filter menu items across all restaurants",
            toolbar, status, table
        );
    }

    void loadMenuItems(TableView<MenuItem> table, String query) {
        ObservableList<MenuItem> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next())
                list.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"),
                    rs.getDouble("Price"), rs.getInt("ResId")));
        } catch (SQLException e) { showAlert("DB Error", e.getMessage()); }
        table.setItems(list);
    }

    // ═════════════════════════════════════════════════════
    //  VIEW 3 — INSERT
    // ═════════════════════════════════════════════════════
    Node buildInsertView() {
        // ── Restaurant Insert ──
        TextField rName = styledField("Restaurant Name");
        TextField rAddr = styledField("Address");
        Label rStatus = statusLabel();
        Button rInsert = styledButton("➕  Insert Restaurant", ACCENT1);
        rInsert.setOnAction(e -> {
            if (rName.getText().isEmpty()) { setStatus(rStatus, "⚠ Fill all fields.", false); return; }
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO Restaurant (Name, Address) VALUES (?, ?)")) {
                ps.setString(1, rName.getText());
                ps.setString(2, rAddr.getText());
                ps.executeUpdate();
                setStatus(rStatus, "✅ Restaurant \"" + rName.getText() + "\" inserted!", true);
                rName.clear(); rAddr.clear();
            } catch (SQLException ex) { setStatus(rStatus, "❌ " + ex.getMessage(), false); }
        });

        GridPane rForm = buildFormGrid(
            new String[]{"Name", "Address"},
            new TextField[]{rName, rAddr}
        );

        VBox rCard = buildCard("🏢  Insert Restaurant",
            "Add a new restaurant to the database",
            rForm,
            new HBox(10, rInsert, rStatus)
        );

        // ── MenuItem Insert ──
        TextField mName  = styledField("Item Name");
        TextField mPrice = styledField("Price (₹)");
        TextField mResId = styledField("Restaurant ID");
        Label mStatus = statusLabel();
        Button mInsert = styledButton("➕  Insert Menu Item", ACCENT3);
        mInsert.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO MenuItem (Name, Price, ResId) VALUES (?, ?, ?)")) {
                ps.setString(1, mName.getText());
                ps.setDouble(2, Double.parseDouble(mPrice.getText()));
                ps.setInt(3,    Integer.parseInt(mResId.getText()));
                ps.executeUpdate();
                setStatus(mStatus, "✅ Menu item \"" + mName.getText() + "\" inserted!", true);
                mName.clear(); mPrice.clear(); mResId.clear();
            } catch (Exception ex) { setStatus(mStatus, "❌ " + ex.getMessage(), false); }
        });

        GridPane mForm = buildFormGrid(
            new String[]{"Item Name", "Price", "Restaurant ID"},
            new TextField[]{mName, mPrice, mResId}
        );

        VBox mCard = buildCard("🍕  Insert Menu Item",
            "Add a new item to a restaurant's menu",
            mForm,
            new HBox(10, mInsert, mStatus)
        );

        VBox layout = new VBox(20, rCard, mCard);
        layout.setPadding(new Insets(0));
        ScrollPane sp = new ScrollPane(layout);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background:transparent;-fx-background-color:transparent;");
        return sp;
    }

    // ═════════════════════════════════════════════════════
    //  VIEW 4 — QUERY BUILDER
    // ═════════════════════════════════════════════════════
    Node buildQueryView() {
        TableView<MenuItem> table = styledTable();
        TableColumn<MenuItem, Integer> colId    = new TableColumn<>("ID");
        TableColumn<MenuItem, String>  colName  = new TableColumn<>("Name");
        TableColumn<MenuItem, Double>  colPrice = new TableColumn<>("Price");
        TableColumn<MenuItem, Integer> colResId = new TableColumn<>("ResID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        styleTableColumns(colId, colName, colPrice, colResId);
        table.getColumns().addAll(colId, colName, colPrice, colResId);
        VBox.setVgrow(table, Priority.ALWAYS);

        Label status = statusLabel();

        // Preset queries
        ComboBox<String> presets = new ComboBox<>();
        presets.setStyle(
            "-fx-background-color:#FFFFFF0D;-fx-border-color:#FFFFFF22;" +
            "-fx-border-radius:6;-fx-background-radius:6;" +
            "-fx-text-fill:" + TEXT_MAIN + ";-fx-font-size:13;"
        );
        presets.getItems().addAll(
            "All Menu Items",
            "Price <= 100",
            "Price <= 200",
            "Items from Cafe Java",
            "Items from Pizza Palace"
        );
        presets.setPromptText("Select a preset query...");
        presets.setPrefWidth(260);

        Button btnRun = styledButton("▶  Run Query", SUCCESS);
        btnRun.setOnAction(e -> {
            String sel = presets.getValue();
            if (sel == null) { setStatus(status, "⚠ Select a query first.", false); return; }
            String sql = switch (sel) {
                case "All Menu Items"        -> "SELECT * FROM MenuItem";
                case "Price <= 100"          -> "SELECT * FROM MenuItem WHERE Price<=100";
                case "Price <= 200"          -> "SELECT * FROM MenuItem WHERE Price<=200";
                case "Items from Cafe Java"  ->
                    "SELECT m.Id,m.Name,m.Price,m.ResId FROM MenuItem m JOIN Restaurant r ON m.ResId=r.Id WHERE r.Name='Cafe Java'";
                case "Items from Pizza Palace" ->
                    "SELECT m.Id,m.Name,m.Price,m.ResId FROM MenuItem m JOIN Restaurant r ON m.ResId=r.Id WHERE r.Name='Pizza Palace'";
                default -> "SELECT * FROM MenuItem";
            };
            loadMenuItems(table, sql);
            setStatus(status, "✅ Query executed: " + sel, true);
        });

        HBox toolbar = new HBox(12, presets, btnRun, status);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        return buildCard(
            "🔍  Query Builder",
            "Run preset SELECT queries on the MenuItem table",
            toolbar, table
        );
    }

    // ═════════════════════════════════════════════════════
    //  VIEW 5 — BULK UPDATE
    // ═════════════════════════════════════════════════════
    Node buildUpdateView() {
        Label status = statusLabel();

        // Update price <= X to Y
        TextField tfFrom = styledField("Price threshold (e.g. 100)");
        TextField tfTo   = styledField("New price (e.g. 200)");
        Button btnBulk = styledButton("✏️  Bulk Update Prices", WARNING);
        btnBulk.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(
                     "UPDATE MenuItem SET Price=? WHERE Price<=?")) {
                ps.setDouble(1, Double.parseDouble(tfTo.getText()));
                ps.setDouble(2, Double.parseDouble(tfFrom.getText()));
                int rows = ps.executeUpdate();
                setStatus(status, "✅ " + rows + " item(s) updated.", true);
            } catch (Exception ex) { setStatus(status, "❌ " + ex.getMessage(), false); }
        });

        VBox bulkCard = buildCard("💰  Bulk Price Update",
            "Set a new price for all items below a threshold",
            buildFormGrid(new String[]{"Where Price ≤", "Set Price To"}, new TextField[]{tfFrom, tfTo}),
            new HBox(10, btnBulk, status)
        );

        // Update by ID
        TextField tfId       = styledField("Menu Item ID");
        TextField tfNewName  = styledField("New Name (optional)");
        TextField tfNewPrice = styledField("New Price (optional)");
        Label status2 = statusLabel();
        Button btnUpdateId = styledButton("✏️  Update by ID", ACCENT3);
        btnUpdateId.setOnAction(e -> {
            if (tfId.getText().isEmpty()) { setStatus(status2, "⚠ Enter an ID.", false); return; }
            try (Connection con = getConnection()) {
                if (!tfNewName.getText().isEmpty()) {
                    PreparedStatement ps = con.prepareStatement("UPDATE MenuItem SET Name=? WHERE Id=?");
                    ps.setString(1, tfNewName.getText()); ps.setInt(2, Integer.parseInt(tfId.getText()));
                    ps.executeUpdate();
                }
                if (!tfNewPrice.getText().isEmpty()) {
                    PreparedStatement ps = con.prepareStatement("UPDATE MenuItem SET Price=? WHERE Id=?");
                    ps.setDouble(1, Double.parseDouble(tfNewPrice.getText())); ps.setInt(2, Integer.parseInt(tfId.getText()));
                    ps.executeUpdate();
                }
                setStatus(status2, "✅ Item ID " + tfId.getText() + " updated.", true);
                tfId.clear(); tfNewName.clear(); tfNewPrice.clear();
            } catch (Exception ex) { setStatus(status2, "❌ " + ex.getMessage(), false); }
        });

        VBox idCard = buildCard("🎯  Update by ID",
            "Update a specific menu item's name or price",
            buildFormGrid(new String[]{"Item ID", "New Name", "New Price"}, new TextField[]{tfId, tfNewName, tfNewPrice}),
            new HBox(10, btnUpdateId, status2)
        );

        VBox layout = new VBox(20, bulkCard, idCard);
        ScrollPane sp = new ScrollPane(layout);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background:transparent;-fx-background-color:transparent;");
        return sp;
    }

    // ═════════════════════════════════════════════════════
    //  VIEW 6 — DELETE
    // ═════════════════════════════════════════════════════
    Node buildDeleteView() {
        Label status1 = statusLabel();
        Label status2 = statusLabel();
        Label status3 = statusLabel();

        // Delete where name starts with letter
        TextField tfLetter = styledField("Starting letter (e.g. P)");
        Button btnDeleteLetter = styledButton("🗑️  Delete by Name Prefix", ACCENT1);
        btnDeleteLetter.setOnAction(e -> {
            String letter = tfLetter.getText().trim();
            if (letter.isEmpty()) { setStatus(status1, "⚠ Enter a letter.", false); return; }
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM MenuItem WHERE Name LIKE ?")) {
                ps.setString(1, letter + "%");
                int rows = ps.executeUpdate();
                setStatus(status1, "✅ " + rows + " item(s) starting with '" + letter + "' deleted.", true);
                tfLetter.clear();
            } catch (SQLException ex) { setStatus(status1, "❌ " + ex.getMessage(), false); }
        });

        VBox card1 = buildCard("🔤  Delete by Name Prefix",
            "Delete all menu items whose name starts with a given letter",
            buildFormGrid(new String[]{"Starts With"}, new TextField[]{tfLetter}),
            new HBox(10, btnDeleteLetter, status1)
        );

        // Delete MenuItem by ID
        TextField tfMenuId = styledField("Menu Item ID");
        Button btnDeleteMenu = styledButton("🗑️  Delete Menu Item", ACCENT1);
        btnDeleteMenu.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM MenuItem WHERE Id=?")) {
                ps.setInt(1, Integer.parseInt(tfMenuId.getText()));
                int rows = ps.executeUpdate();
                setStatus(status2, rows > 0 ? "✅ Menu item deleted." : "⚠ ID not found.", rows > 0);
                tfMenuId.clear();
            } catch (Exception ex) { setStatus(status2, "❌ " + ex.getMessage(), false); }
        });

        VBox card2 = buildCard("🍕  Delete Menu Item by ID",
            "Remove a specific menu item by its ID",
            buildFormGrid(new String[]{"Item ID"}, new TextField[]{tfMenuId}),
            new HBox(10, btnDeleteMenu, status2)
        );

        // Delete Restaurant by ID
        TextField tfResId = styledField("Restaurant ID");
        Button btnDeleteRes = styledButton("🗑️  Delete Restaurant", "#B71C1C");
        btnDeleteRes.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM Restaurant WHERE Id=?")) {
                ps.setInt(1, Integer.parseInt(tfResId.getText()));
                int rows = ps.executeUpdate();
                setStatus(status3, rows > 0 ? "✅ Restaurant + its menu items deleted (CASCADE)." : "⚠ ID not found.", rows > 0);
                tfResId.clear();
            } catch (Exception ex) { setStatus(status3, "❌ " + ex.getMessage(), false); }
        });

        VBox card3 = buildCard("🏢  Delete Restaurant by ID",
            "⚠ Also deletes all menu items for this restaurant (CASCADE)",
            buildFormGrid(new String[]{"Restaurant ID"}, new TextField[]{tfResId}),
            new HBox(10, btnDeleteRes, status3)
        );

        VBox layout = new VBox(20, card1, card2, card3);
        ScrollPane sp = new ScrollPane(layout);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background:transparent;-fx-background-color:transparent;");
        return sp;
    }

    // ═════════════════════════════════════════════════════
    //  SHARED HELPERS
    // ═════════════════════════════════════════════════════
    GridPane buildFormGrid(String[] labels, TextField[] fields) {
        GridPane grid = new GridPane();
        grid.setHgap(16); grid.setVgap(12);
        grid.setPadding(new Insets(4, 0, 4, 0));
        for (int i = 0; i < labels.length; i++) {
            Label lbl = new Label(labels[i]);
            lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
            lbl.setTextFill(Color.web(TEXT_DIM));
            lbl.setMinWidth(130);
            grid.add(lbl,      0, i);
            grid.add(fields[i], 1, i);
        }
        return grid;
    }

    @SafeVarargs
    final <T> void styleTableColumns(TableColumn<T, ?>... cols) {
        for (TableColumn<T, ?> col : cols) {
            col.setStyle(
                "-fx-text-fill:" + TEXT_MAIN + ";" +
                "-fx-font-size:13;" +
                "-fx-alignment:CENTER-LEFT;"
            );
            col.setResizable(true);
        }
    }

    void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}