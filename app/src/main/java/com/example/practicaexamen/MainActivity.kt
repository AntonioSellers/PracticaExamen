package com.example.practicaexamen


import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.practicaexamen.component.Recipe
import com.example.practicaexamen.databinding.ActivityMainBinding
import pub.devrel.easypermissions.EasyPermissions

private const val CAMARA_PERMISSIONS_CODE = 123

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var recipeHistory: MutableList<Recipe> = mutableListOf()
    private var fragment: itemFragment? = null
    private var isVisibility: Boolean = false

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode == RESULT_OK){
            val imgBimap = result.data?.extras?.get("data") as Bitmap
            binding.imgPhoto.setImageBitmap(imgBimap)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragment = supportFragmentManager.findFragmentById(R.id.main_container) as? itemFragment

        binding.imgButton.setOnClickListener {
            requestPermissionsCamera()
        }

        binding.saveButton.setOnClickListener {
            if (isFieldValid()){
                saveData()
            }
        }

        binding.recipeButton.setOnClickListener {
            isVisible()
            val fragment = itemFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_container, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    // Funciones Guardado

    private fun isFieldValid(): Boolean {
        val recipeText = binding.inputRecipe.text.toString()
        val ingredientText = binding.inputIngredient.text.toString()
        val img = binding.imgPhoto.drawable

        if (recipeText.isEmpty() || ingredientText.isEmpty() || img == null){
            binding.textError.text = "Datos sin completar"
            return false
        }

        binding.textError.text = "Guardado con éxito"
        return true

    }

    private fun saveData() {
        try {
            val recipe = binding.inputRecipe.text.toString()
            val ingredient = binding.inputIngredient.text.toString()
            val img = binding.imgPhoto.drawable

            val objectRecipe = Recipe(recipe, ingredient, img)

            if (objectRecipe != null) {
                recipeHistory.add(objectRecipe)
            }
        } catch (e: NumberFormatException){
            e.printStackTrace()
        }
    }

    // Funciones Camara

    private fun requestPermissionsCamera() {
        if (hasCamaraPermission()) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraActivity.launch(cameraIntent)
        } else {
            EasyPermissions.requestPermissions(this, "Necesitamos permisos para abrir la camara", CAMARA_PERMISSIONS_CODE, android.Manifest.permission.CAMERA)
        }
    }

    private fun hasCamaraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)
    }

    //Funciones viajar

    fun getRecipe(): MutableList<Recipe> {
        return recipeHistory
    }

    fun isVisible(){
        if (isVisibility){
            binding.mainContainer.visibility = View.INVISIBLE
            binding.mainFragment.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.mainContainer.visibility = View.VISIBLE
    }

}