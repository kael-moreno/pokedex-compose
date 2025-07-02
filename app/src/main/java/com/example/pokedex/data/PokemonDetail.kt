package com.example.pokedex.data

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val abilities: List<AbilitySlot>,
    val cries: Cries,
    val forms: List<NamedAPIResource>,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndex>,
    @SerializedName("held_items")
    val heldItems: List<HeldItem>,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,
    val moves: List<MoveSlot>,
    val order: Int,
    @SerializedName("past_abilities")
    val pastAbilities: List<PastAbility>,
    @SerializedName("past_types")
    val pastTypes: List<PastType>,
    val species: NamedAPIResource,
    val sprites: Sprites,
    val stats: List<StatSlot>,
    val types: List<TypeSlot>
)

data class AbilitySlot(
    val ability: NamedAPIResource,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int
)

data class Cries(
    val latest: String,
    val legacy: String
)

data class GameIndex(
    @SerializedName("game_index")
    val gameIndex: Int,
    val version: NamedAPIResource
)

data class HeldItem(
    val item: NamedAPIResource,
    @SerializedName("version_details")
    val versionDetails: List<VersionDetail>
)

data class VersionDetail(
    val rarity: Int,
    val version: NamedAPIResource
)

data class MoveSlot(
    val move: NamedAPIResource,
    @SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetail>
)

data class VersionGroupDetail(
    @SerializedName("level_learned_at")
    val levelLearnedAt: Int,
    @SerializedName("move_learn_method")
    val moveLearnMethod: NamedAPIResource,
    @SerializedName("version_group")
    val versionGroup: NamedAPIResource
)

data class PastAbility(
    val abilities: List<AbilitySlot>,
    val generation: NamedAPIResource
)

data class PastType(
    val generation: NamedAPIResource,
    val types: List<TypeSlot>
)

data class TypeSlot(
    val slot: Int,
    val type: NamedAPIResource
)

data class StatSlot(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: NamedAPIResource
)

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_female")
    val backFemale: String?,
    @SerializedName("back_shiny")
    val backShiny: String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale: String?,
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?,
    val other: OtherSprites?
)

data class OtherSprites(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld?,
    val home: Home?,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?,
    val showdown: Showdown?
)

data class DreamWorld(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?
)

data class Home(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?
)

data class Showdown(
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_female")
    val backFemale: String?,
    @SerializedName("back_shiny")
    val backShiny: String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale: String?,
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?
)

data class NamedAPIResource(
    val name: String,
    val url: String
)

