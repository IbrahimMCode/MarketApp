package com.example.marketapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.marketapp.Adapters.CategoryPagerAdapter;
import com.example.marketapp.Adapters.ItemAdapter;
import com.example.marketapp.Adapters.OrderAdapter;
import com.example.marketapp.api.models.Category;
import com.example.marketapp.api.models.Item;
import com.example.marketapp.api.models.Cart;
import com.example.marketapp.api.models.Market;
import com.example.marketapp.api.models.Order;
import com.example.marketapp.api.models.OrderItem;
import com.example.marketapp.api.models.Product;
import com.example.marketapp.api.models.Solution;
import com.example.marketapp.api.models.SubCategory;

import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.services.CategoryAPIInterface;
import com.example.marketapp.api.services.OrderAPIInterface;
import com.example.marketapp.api.services.ProductAPIInterface;
import com.example.marketapp.managers.SessionManager;
import com.example.marketapp.managers.UserManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonElement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MarketActivity extends AppCompatActivity implements ItemAdapter.IItemAdapterCallback, OrderAdapter.IOrderAdapterCallback
{
    private DrawerLayout drawer;
    private RelativeLayout rlCart;
    private Button btnCompleteOrder;


    private TextView txtCount;
    private TextView txtTotal;
    private RecyclerView rvOrder;
    private TextView txtClearAll;
    private OrderAdapter orderAdapter;
    private List<Category> categoryList;
    private ArrayList<SubCategory> subCategoryList;

    private ArrayList<Item> itemList;
    private ArrayList<Solution> solutionList;
    private ArrayList<Cart> orderList;
    private List<Product> productList;

    OrderAPIInterface orderAPIInterface;
    ProductAPIInterface productAPIInterface;
    CategoryAPIInterface categoryAPIInterface;

    Intent intent;
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart, menu);


        final View actionCart = menu.findItem(R.id.actionCart).getActionView();
        txtCount = (TextView) actionCart.findViewById(R.id.txtCount);
        rlCart = (RelativeLayout) actionCart.findViewById(R.id.rlCart);

        rlCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                handleOrderDrawer();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                UserManager.getInstance().ClearLoginPref();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.orderHistory:
                intent = new Intent(this, AccountActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onIncreaseDecreaseCallback()
    {
        updateOrderTotal();
        updateBadge();
    }
    @Override
    public void onItemCallback(Item item)
    {
//        dialogItemDetail(item);
    }

    @Override
    public void onAddItemCallback(ImageView imageView, Item item)
    {
        addItemToCart(item, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        simpleProgressBar.setVisibility(View.VISIBLE);

        categoryList = new ArrayList<Category>();
        subCategoryList = new ArrayList<SubCategory>();
        itemList = new ArrayList<Item>();
        solutionList = new ArrayList<>();
        orderList = new ArrayList<>();
        productList = new ArrayList<Product>();

        orderAPIInterface = APIClient.getClient().create(OrderAPIInterface.class);
        productAPIInterface = APIClient.getClient().create(ProductAPIInterface.class);


        Call<List<Product>> callproducts = productAPIInterface.GetMarketProducts(SessionManager.getInstance().getSelectedMarketId());
        callproducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> callproducts, Response<List<Product>> responseproducts) {
                productList = responseproducts.body();
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });

        categoryAPIInterface = APIClient.getClient().create(CategoryAPIInterface.class);
        Call<List<Category>> call1 = categoryAPIInterface.GetCategories();
        call1.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        categoryList = response.body();

                        drawer = findViewById(R.id.dlMain);
                        txtTotal = findViewById(R.id.txtTotal);
                        rvOrder = findViewById(R.id.rvOrder);
                        txtClearAll = findViewById(R.id.txtClearAll);

                        for (Product p : productList) {
//                    for (SubCategory subCategory : subCategoryList) {
//                        if (subCategory.getId().equals(p.getSubCategoryId())) {
                            itemList.add(new Item(p.getId(), 0, p.getSubCategoryId(), p.getName(), p.getPrice(), p.getImagePath()));
//                        }
//                    }
                        }
                        for (Category categoryItem : categoryList) {
                            subCategoryList.addAll(categoryItem.getSubCategories());
                            ArrayList<SubCategory> tempSubCategoryList = new ArrayList<>();

                            // Temporary list of the current items
                            ArrayList<Item> tempItemList = new ArrayList<>();

                            // Temporary map
                            Map<SubCategory, ArrayList<Item>> itemMap = new HashMap<SubCategory, ArrayList<Item>>();

                            // categoryId == 1 means category with all items and sub-categories.
                            // That's why i add all the sub-categories and items directly.
                            if (categoryItem.getId() == 1)
                            {
                                itemMap = getItemMap(subCategoryList);

                                solutionList.add(new Solution(categoryItem, subCategoryList, itemList, itemMap));
                            }
                            else
                            {
                                tempSubCategoryList = getSubCategoryListByCategoryId(categoryItem.getId());
                                tempItemList = getItemListByCategoryId(categoryItem.getId());
                                itemMap = getItemMap(tempSubCategoryList);

                                solutionList.add(new Solution(categoryItem, tempSubCategoryList, tempItemList, itemMap));
                            }

                        }

                        rvOrder.setLayoutManager(new LinearLayoutManager(MarketActivity.this));
                        orderAdapter = new OrderAdapter(MarketActivity.this, orderList);
                        rvOrder.setAdapter(orderAdapter);

                        // Get the ViewPager and set it's CategoryPagerAdapter so that it can display items
                        ViewPager vpItem = (ViewPager) findViewById(R.id.vpItem);
                        CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager(), MarketActivity.this, solutionList);
                        vpItem.setOffscreenPageLimit(categoryPagerAdapter.getCount());
                        vpItem.setAdapter(categoryPagerAdapter);

                        // Give the TabLayout the ViewPager
                        TabLayout tabCategory = findViewById(R.id.tabCategory);
                        tabCategory.setupWithViewPager(vpItem);

                        for (int i = 0; i < tabCategory.getTabCount(); i++)
                        {
                            TabLayout.Tab tab = tabCategory.getTabAt(i);
                            tab.setCustomView(categoryPagerAdapter.getTabView(i));
                        }
                    }
                }
                simpleProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
        btnCompleteOrder = findViewById(R.id.btnCompleteOrder);
        btnCompleteOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                makeOrder();
            }
        });

        txtClearAll = findViewById(R.id.txtClearAll);
        txtClearAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (orderList.size() > 0)
                {
                    dialogClearAll();
                }
            }
        });
    }




    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        } else
        {
            super.onBackPressed();
        }
    }


    private ArrayList<SubCategory> getSubCategoryListByCategoryId(int categoryId)
    {
        ArrayList<SubCategory> tempSubCategoryList = new ArrayList<SubCategory>();

        for (SubCategory subCategory : subCategoryList)
        {
            if (subCategory.getCategoryId() == categoryId)
            {
                tempSubCategoryList.add(new SubCategory(subCategory));
            }
        }

        return tempSubCategoryList;
    }

    /**
     * Gets the items belonging to a category
     *
     * @param categoryId The id of the current category.
     *
     * Returns the items belonging to that category as a list using the id of the current category.
     * @return a list of items
     */
    private ArrayList<Item> getItemListByCategoryId(int categoryId)
    {
        ArrayList<Item> tempItemList = new ArrayList<Item>();

        for (Item item : itemList)
        {
            if (item.categoryId == categoryId)
            {
                tempItemList.add(item);
            }
        }

        return tempItemList;
    }

    /**
     * Gets the items belonging to a sub-category
     *
     * @param subCategoryId The id of the current sub-category.
     *
     * Returns the item belonging to that sub-category as a list
     * using the id of the current sub-category.
     * @return a list of items
     */




    private ArrayList<Item> getItemListBySubCategoryId(int subCategoryId)
    {
        ArrayList<Item> tempItemList = new ArrayList<Item>();

        for (Item item : itemList)
        {
            if (item.subCategoryId == subCategoryId)
            {
                tempItemList.add(item);
            }
        }

        return tempItemList;
    }

    /**
     * Gets a Map which has the key is the sub-category
     * and the value is the items of that sub-category.
     * That Map will also be used in the Sectioned RecyclerView.
     *
     * @param subCategoryList sub-categories which is owned by a category
     *
     * @return a Map
     */
    private Map<SubCategory, ArrayList<Item>> getItemMap(ArrayList<SubCategory> subCategoryList)
    {
        Map<SubCategory, ArrayList<Item>> itemMap = new HashMap<SubCategory, ArrayList<Item>>();

        for (SubCategory subCategory : subCategoryList)
        {
            itemMap.put(subCategory, getItemListBySubCategoryId(subCategory.getId()));
        }

        return itemMap;
    }

    /*
     * Adds the item to order list.
     */
    private void addItemToCart(Item item, int quantity)
    {
        boolean isAdded = false;

        for (Cart order : orderList)
        {
            if (order.item.id == item.id)
            {
                //if item already added to cart, dont add new order
                //just add the quantity
                isAdded = true;
                order.quantity += quantity;
                order.extendedPrice += item.unitPrice;
                break;
            }
        }

        //if item's not added yet
        if (!isAdded)
        {
            orderList.add(new Cart(item, quantity));
        }

        orderAdapter.notifyDataSetChanged();
        rvOrder.smoothScrollToPosition(orderList.size() - 1);
        updateOrderTotal();
        updateBadge();
    }

    /*
     * Updates the value of the badge
     */
    private void updateBadge()
    {
        if (orderList.size() == 0)
        {
            txtCount.setVisibility(View.INVISIBLE);
        } else
        {
            txtCount.setVisibility(View.VISIBLE);
            txtCount.setText(String.valueOf(orderList.size()));
        }
    }

    /*
     * Gets the total price of all products added to the cart
     */
    private double getOrderTotal()
    {
        double total = 0.0;

        for (Cart order : orderList)
        {
            total += order.extendedPrice;
        }

        return total;
    }

    /*
     * Updates the total price of all products added to the cart
     */
    private void updateOrderTotal()
    {
        double total = getOrderTotal();
        txtTotal.setText(String.format("%d", (long)total) + "LBP");
    }

    /*
     * Closes or opens the drawer
     */
    private void handleOrderDrawer()
    {
        if (drawer != null)
        {
            if (drawer.isDrawerOpen(GravityCompat.END))
            {
                drawer.closeDrawer(GravityCompat.END);
            } else
            {
                drawer.openDrawer(GravityCompat.END);
            }
        }
    }

    /*
     * Makes the cart empty
     */
    private void dialogClearAll()
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder = new AlertDialog.Builder(MarketActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else
        {
            builder = new AlertDialog.Builder(MarketActivity.this);
        }
        builder.setTitle("Clear All")
                .setMessage("Are you sure you want to clear your cart items?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        clearAll();
//                        showMessage(true, getString("Clean Cart"));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // do nothing
                    }
                })
                .show();
    }

    private void clearAll()
    {
        orderList.clear();
        orderAdapter.notifyDataSetChanged();

        updateBadge();
        updateOrderTotal();
        handleOrderDrawer();
    }


    private void makeOrder(){
        if (orderList.isEmpty()){
            Toast.makeText(this, "Please Add Items to Cart", Toast.LENGTH_LONG).show();
        }else{
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                builder = new AlertDialog.Builder(MarketActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else
            {
                builder = new AlertDialog.Builder(MarketActivity.this);
            }
            builder.setTitle("Purchase Order?")
                    .setMessage("Are you sure you want to confirm your order?\r\nTotal Price : "+ String.valueOf(getOrderTotal()) + "LBP\r\nEstimated Arrival Time : 45 Minutes\r\n")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Order order = new Order();
                            order.setMarketId(SessionManager.getInstance().getSelectedMarketId());
                            order.setTotalPrice((int) getOrderTotal());
                            List<OrderItem> orderItems = new ArrayList<>();
                            for(Cart c : orderList){
                                orderItems.add(new OrderItem(c.item.id, (int) c.item.unitPrice, c.quantity));
                            }
                            order.setOrderItems(orderItems);
                            Call<JsonElement> addordercall = orderAPIInterface.PostOrder(order, UserManager.getInstance().GetToken());
                            addordercall.enqueue(new Callback<JsonElement>() {
                                @Override
                                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                    clearAll();
                                    Intent intent = new Intent(MarketActivity.this, AccountActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                @Override
                                public void onFailure(Call<JsonElement> call, Throwable t) {

                                }
                            });

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // do nothing
                        }
                    })
                    .show();
        }
    }




}
