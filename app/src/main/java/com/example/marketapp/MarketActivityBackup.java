package com.example.marketapp;

import androidx.appcompat.app.AppCompatActivity;

public class MarketActivityBackup extends AppCompatActivity {
//    RecyclerView MainCategoriesRecyclerView;
//    RecyclerView SubCategoriesRecyclerView;
//    CategoryAPIInterface categoryAPIInterface;
//    List<Category> categoryList = new ArrayList<Category>();
//    MainCategoriesAdapter mainCategoriesAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_market);
//        categoryAPIInterface = APIClient.getClient().create(CategoryAPIInterface.class);
//
//        MainCategoriesRecyclerView = findViewById(R.id.maincategories_recyclerview);
//        //        this.getSupportActionBar().setTitle("");
//        RetrieveCategories();
//
//        MainCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        MainCategoriesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//        mainCategoriesAdapter = new MainCategoriesAdapter(this,categoryList);
//
//
//
//
//
//
//
//    }
//
//
//
//    public void RetrieveCategories(){
//        Call<List<Category>> call1 = categoryAPIInterface.GetCategories();
//        call1.enqueue(new Callback<List<Category>>() {
//            @Override
//            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
//                if (response.isSuccessful()){
//                    categoryList.addAll(response.body());
//                    MainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
//
//                    SubCategoriesRecyclerView = findViewById(R.id.subcategories_recyclerview);
//                    SubCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
//                    SubCategoriesRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
//
//                    ArrayList<SubCategory> subCategories= new ArrayList<>();
//                    for (Category c : categoryList
//                         ) {
//                        subCategories.addAll(c.getSubCategories());
//                    }
//
//                    SubCategoriesRecyclerView.setAdapter(new SubCategoryAdapter(getApplicationContext(), subCategories));
//
//
//                }else {
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.ConstraintLayout), "API Error", Snackbar.LENGTH_INDEFINITE);
//                    snackbar.setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            snackbar.dismiss();
//                        }
//                    });
//                    snackbar.show();
//                }
//            }
//            @Override
//            public void onFailure(Call<List<Category>> call, Throwable t) {
//                call.cancel();
//            }
//        });
//    }
}